from flask import Flask , request , render_template


app = Flask(__name__)


@app.route('/')
def homePage():
    return render_template("index.html", title='Главная страница', content='Для быстрой проверки!')

@app.get('/login')
def loginUser():
    return render_template("index.html", title='Вход', content='Вход!')  

@app.post('/login')
def loginStatus():
    return 'POST'

@app.get('/register')
def registerUser():
    return render_template("index.html", title='Регистрация', content='Регистрация!')  

@app.post('/register')
def registerStatus():
    return 'POST'

@app.route('/logout' , methods=['GET', 'POST' , 'DELETE'])
def logoutUser():
    if request.method == 'GET':
       return render_template("index.html", title='Выход!!!', content='Выход')  
    if request.methods == 'POST':
       return 'POST'
    if request.methods == 'DELETE':
       return 'DELETE'

@app.get('/profile/me')
def profileUser():
    return render_template("index.html", title='))))', content='Здесь мог быть ваш профиль!')  

@app.put('/profile/me')
def profileUserChange():
    return 'PUT'

@app.delete('/profile/me')
def profileUserRemove():
    return 'DELETE'

@app.get('/profile/me/favouties')
def favoutieItem():
    return render_template("index.html", title='Топ шмот', content='Скрытая фича)')  

@app.post('/profile/me/favouties')
def favoutieItemAdded():
    return 'POST'

@app.delete('/profile/me/favouties')
def favoutiesAllItemsRemove():
    return 'DELETE'

@app.patch('/profile/me/favouties')
def favoutiesItemChange():
    return 'PATCH'

@app.delete('/profile/me/favouties/<favourite_id>')
def favoutieItemRemove():
    return 'DELETE'

@app.get('/items')
def itemsList():
    return render_template("index.html", title='Весь шмот', content='Шмотки')     

@app.post('/items')
def itemsAdded():
    return 'POST'

@app.get('/items/<item_id>')
def itemInfo():
    return render_template("index.html", title='Метод не найден', content='ИД нет(!')      

@app.delete('/items/<item_id>')
def itemRemove():
   return 'DELETE'

@app.get('/leasers')
def everyleasersInfo():
    return render_template("index.html", title='Кто-то', content='Здесь кто-то есть')   

@app.get('/leasers/<leaser_id>')
def leaserInfo():
    return render_template("index.html", title='Явно не я', content='Это кто-то из юзеров')  

@app.get('/contracts')
def contractsList():
    return render_template("index.html", title='Контракты', content='печати нет')     

@app.post('/contracts')
def contractAdded():
    return 'POST'

@app.get('/contracts/<contract_id>')
def contractInfo():
    return render_template("index.html", title='Уже было', content='буу')    

@app.put('/contracts/<contract_id>')
def contractChange():
    return 'PUT'

@app.get('/search')
def search():
    return render_template("index.html", title='Поиск', content='Найдено')     

@app.post('/search')
def searchRequest():
    return 'POST'

@app.post('/complain')
def report():
    return 'POST'

@app.get('/compare')
def compare():
    return render_template("index.html", title='...', content='Вроде воркает')      

@app.put('/compare')
def compareItem():
    return 'PUT'

if __name__=='__main__':
    app.run(debug=True)