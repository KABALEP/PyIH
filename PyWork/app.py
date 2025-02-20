from flask import Flask, redirect , request , render_template , session , jsonify
import sqlite3
from functools import wraps
from sqlalchemy import select
from database import init_db, db_session
import models
import celery_task


app = Flask(__name__) 
app.secret_key= 'F@MHdXIPVYom'

def dict_factory(cursor, row):
    d={}
    for idx, col in enumerate(cursor.description):
        d[col[0]] = row[idx]

    return d

class DatabaseConnection():
    
    def __init__(self, db_name):
        self.con=sqlite3.connect(db_name)
        self.con.row_factory = dict_factory
        self.cur=self.con.cursor()

    def __enter__(self):
        return self.cur
    
    def __exit__(self, exc_type, exc_value, traceback):
        self.con.commit()
        self.con.close()

def login_required(func):
    def wrapper(*args, **kwargs):
        if 'user_login' not in session:
            return redirect('/login')
        result= func(*args, **kwargs)
        return result
    return wrapper

@app.route('/')
def homePage():
    return render_template("index.html")

@app.get('/login')
def loginUser():
    return render_template("login.html")  

@app.post('/login')
def loginStatus():
    user_name = request.form['login']
    user_password = request.form['password']

    init_db()

    user_checker = select(models.User).where(
        models.User.login == user_name,
        models.User.password == user_password
        )
    user_data=db_session.execute(user_checker).scalar_one_or_none()
    if user_data:
        session['user_login']= user_data.login
        session['user_id']=user_data.id
        return redirect('/') 
    else:
        error_message='invalid login or password'
        return redirect('/login', error_message)

@app.get('/register')
def registerUser():
    return render_template("register.html")  

@app.post('/register')
def registerStatus():
    user_register_dict=dict(request.form)
    init_db()
    user= models.User(**user_register_dict)
    db_session.add(user)
    db_session.commit()
    return redirect('/login')

@login_required
@app.route('/logout' , methods=['GET', 'POST' , 'DELETE'])
def logoutUser():
    session.clear()
    return render_template('index.html')

@login_required
@app.get('/me')
def profileUser():
    init_db()
    user_info_query = select(models.User).where(models.User.id==session['user_id'])
    user_info = list(db_session.execute(user_info_query).scalars())
    return render_template("me.html",user_info=user_info)  

@app.put('/me')
def profileUserChange():
    return 'PUT'

@app.delete('/me')
def profileUserRemove():
    return 'DELETE'

@login_required
@app.get('/me/favouties')
def favoutieItem():
    return render_template("favouties.html")  

@app.post('/me/favouties')
def favoutieItemAdded():
    return 'POST'

@app.delete('/me/favouties')
def favoutiesAllItemsRemove():
    return 'DELETE'

@app.patch('/me/favouties')
def favoutiesItemChange():
    return 'PATCH'

@login_required
@app.delete('/me/favouties/<favourite_id>')
def favoutieItemRemove():
    return 'DELETE'

@login_required
@app.get('/items')
def itemsList():
    init_db()
    items_list_query = select(models.Item)
    items_list = list(db_session.execute(items_list_query).scalars())
    item_owner = session['user_id']
    return render_template("items.html", items_list=items_list, item_owner=item_owner)     

@app.post('/items')
def itemsAdded():
    item_add_list=dict(request.form)
    init_db()
    item_add=models.Item(**item_add_list)
    db_session.add(item_add)
    db_session.commit()
    return redirect('items')

@app.get('/items/<item_id>')
def itemInfo(item_id):
    init_db()
    item_list=select(models.Item).where(models.Item.id == item_id)
    item_query=list(db_session.execute(item_list).scalars())
    return render_template('one_item.html', item_query=item_query)      

@login_required
@app.delete('/items/<item_id>')
def itemRemove():
   return 'DELETE'

@login_required
@app.get('/leasers')
def everyleasersInfo():
    init_db()
    leasers_query=select(models.User).where(models.User.id != session['user_id'])
    leasers_list=list(db_session.execute(leasers_query).scalars())
    not_belong_leaser=session['user_id']
    return render_template("leasers.html",leasers_list=leasers_list ,not_belong_leaser=not_belong_leaser)   

@app.get('/leasers/<leaser_id>')
def leaserInfo():
    return render_template("index.html")  

@login_required
@app.get('/contracts')
def contractsList():
    init_db()
    contracts_query= select(models.Contract)
    contracts_list= list(db_session.execute(contracts_query).scalars())
    return render_template("contracts.html", contracts_list=contracts_list)     

@app.post('/contracts')
def contractAdded():
    contract_add_list=dict(request.form)
    init_db()
    contract_add=models.Contract(**contract_add_list)
    db_session.add(contract_add)
    db_session.commit()
    return redirect('/contracts')

@app.get('/contracts/<contract_id>')
def contractInfo():
    return render_template("index.html")    

@app.put('/contracts/<contract_id>')
def contractChange():
    return 'PUT'

@app.get('/search')
def search():
    return render_template("search.html")     

@app.post('/search')
def searchRequest():
    return 'POST'

@app.post('/complain')
def report():
    return 'POST'

@login_required
@app.get('/compare')
def compare():
    return render_template("compare.html")      

@app.put('/compare')
def compareItem():
    return 'PUT'

@login_required
@app.get('/feedback')
def feedback():
    return render_template("feedback.html")  
 
@login_required
@app.post('/feedback')
def leaveFeedback():
    feedback_list=dict(request.form)
    init_db()
    feedback=models.Feedback(**feedback_list)
    db_session.add(feedback)
    db_session.commit()
    return redirect('/feedback')

@app.get('/add_task')
def set_task():
        celery_task.add.deley(1,2)
        


if __name__=='__main__':
    app.run(debug=True, host='0.0.0.0')