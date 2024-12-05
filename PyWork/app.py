from flask import Flask, redirect , request , render_template , session , jsonify
import sqlite3
from functools import wraps
from sqlalchemy import select
from database import init_db, db_session
import models


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

# class DatabaseManager:

#      connection_string='rentdb.db'

#      def insert(self, table_name, data_dict):
#         with DatabaseConnection(self.connection_string) as db_cur:
#             query=f'INSERT INTO {table_name} ('
#             query+=', '.join(data_dict.keys())
#             query+=') VALUES ('
#             query+=', '.join([f':{itm}' for itm in data_dict.keys()])
#             query+=')'
#             db_cur.execute(query, data_dict)

#      def select(self, table_name, condition=None):
#         if condition is None:
#             condition={}
#         with DatabaseConnection(self.connection_string) as db_cur:
#             query= f'SELECT * FROM {table_name}'
#             if condition:
#                 query+=' WHERE '
#                 itms=[]
#                 for key, value in condition.items():
#                     itms.append(f'{key} = ?')
#                 query+=' AND '.join(itms)
#             db_cur.execute(query, tuple(value for value in condition.values()))
#             return db_cur.fetchall()
        
# db_connector= DatabaseManager()

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
    # user_data = db_connector.select('user',{'login': user_name, 'password': user_password})
    if user_data:
        session['user_login']= user_data.login
        session['user_id']=user_data.id
        return jsonify({'message': 'Success'}), 200 
    else:
        return jsonify({'error': 'User not found or invalid credentials'}), 401

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
    # db_connector.insert('user',user_register_dict)
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
    # user_info = db_connector.select('user',{'login':session['user_login']})
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
    # items_list = db_connector.select('item')
    item_owner = session['user_id']
    return render_template("items.html", items_list=items_list, item_owner=item_owner)     

@app.post('/items')
def itemsAdded():
    item_add_list=dict(request.form)
    init_db()
    item_add=models.Item(**item_add_list)
    db_session.add(item_add)
    db_session.commit()
    # db_connector.insert('item',item_add_list)
    return redirect('items')

@app.get('/items/<item_id>')
def itemInfo():
    return render_template('index.html')      

@login_required
@app.delete('/items/<item_id>')
def itemRemove():
   return 'DELETE'

@app.get('/leasers')
def everyleasersInfo():
    init_db()
    leasers_query=select(models.User).where(models.User.id != session['user_id'])
    leasers_list=list(db_session.execute(leasers_query).scalars())
    # leasers_list = db_connector.select('user')
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
    # contracts_list=db_connector.select('contract')
    return render_template("contracts.html", contracts_list=contracts_list)     

@app.post('/contracts')
def contractAdded():
    contract_add_list=dict(request.form)
    init_db()
    contract_add=models.Contract(**contract_add_list)
    db_session.add(contract_add)
    db_session.commit()
    # db_connector.insert('contract',contract_add_list)
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
    # db_connector.insert('feedback',feedback_list)
    return redirect('/feedback')


if __name__=='__main__':
    app.run(debug=True)