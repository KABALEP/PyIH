import json
import os
import random
import string
import aiofiles
from fastapi import FastAPI, HTTPException , Request , Form
from fastapi.responses import HTMLResponse, RedirectResponse
from fastapi.templating import Jinja2Templates
from typing import Annotated
import motor.motor_asyncio

db_client= motor.motor_asyncio.AsyncIOMotorClient("localhost", 27017, username='root', password='example')
app_db=db_client['url_shortener']
collection=app_db['urls']

app = FastAPI()

templates = Jinja2Templates(directory="tamplates")

if not os.path.exists("urls.json"):
    with open("urls.json", "w") as file:
        file.write("{}")

@app.get('/')
async def root(request: Request):
    return templates.TemplateResponse(request=request, name="index.html")

@app.post("/")
async def decrease_link(request: Request, long_url: Annotated[str, Form()]):
    short_url = "".join(
        random.choice(string.ascii_letters + string.digits)
        for _ in range(5))
    await collection.insert_one({'short_url':short_url,'long_url':long_url})
    
    return {"message": f"Short URL is {short_url}"}

@app.get("/{short_url}")
async def short_link(short_url:str):
    collection_data= await collection.find_one({'short_url':short_url})
    redirect_url=collection_data.get('long_url') if collection_data else None
    collection_data = await collection.update_one({'short_url':short_url}, {"$inc":{'clicks':1}})
    print(
        f"Short Url:{short_url} | Clicks:{collection_data.modified_count}"
    )
    if redirect_url is None:
        return HTTPException(status_code=404, detail="URL not found")
    else:
        return RedirectResponse(redirect_url)
    
@app.get("/{short_url}/stats")
async def stats(request: Request, short_url:str):
    collection_data = await collection.find_one({'short_url':short_url})
    if collection_data is None:
        raise HTTPException(status_code=404, detail="Url not found")
    return templates.TemplateResponse(request=request, name="stats.html", context={"url_data":collection_data})
    
@app.post("/{short_url}/stats")
async def changes_stats(request: Request, short_url: str ,long_url: Annotated[str, Form()]):

    await collection.update_one(filter={'short_url':short_url},update={"$set":{'long_url':long_url}})
    collection_data = await collection.find_one({'short_url':short_url})
    return templates.TemplateResponse(request=request, name="stats.html", context={"url_data":collection_data})