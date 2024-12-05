from sqlalchemy import create_engine, Column, Integer, String, ForeignKey, REAL
from database import Base
from sqlalchemy.orm import mapped_column

class User(Base):
    __tablename__ = 'user'  

    id = Column(Integer, primary_key=True, autoincrement=True)
    login = Column(String, nullable=False, unique=True)
    password = Column(String, nullable=False)
    ipn = Column(Integer, nullable=False, unique=True)
    full_name = Column(String, nullable=False)
    contacts = Column(String, nullable=False)
    photo = Column(String, nullable=True)

    def __init__(self, login, password, ipn, full_name, contacts, photo):
        self.login=login
        self.password=password
        self.ipn=ipn
        self.full_name=full_name
        self.contacts=contacts
        self.photo=photo


class Item(Base):
    __tablename__ = 'item'
    
    id = Column(Integer, primary_key=True, autoincrement=True, unique=True, nullable=False)
    photo = Column(String, nullable=False)
    name = Column(String, nullable=False)
    description = Column(String, nullable=False)
    price_hour = Column(REAL, nullable=True)
    price_day = Column(REAL, nullable=True)
    price_week = Column(REAL, nullable=True)
    price_month = Column(REAL, nullable=True)
    owner = mapped_column(ForeignKey('user.id'), nullable=False)

    def __init__(self, photo, name, description, price_hour, price_day, price_week, price_month, owner):
       self.photo=photo
       self.name=name
       self.description=description
       self.price_hour=price_hour
       self.price_day=price_day
       self.price_week=price_week
       self.price_month=price_month
       self.owner=owner
       

class Contract(Base):
    __tablename__ = 'contract'
    
    id = Column(Integer, primary_key=True, autoincrement=True, unique=True, nullable=False)
    text = Column(String(255), nullable=False)
    start_date = Column(String, nullable=False)
    end_date = Column(String, nullable=False)
    leaser = mapped_column(ForeignKey('user.id'), nullable=False)
    taker = mapped_column(ForeignKey('user.id'), nullable=False)
    item = mapped_column(ForeignKey('item.id'), nullable=False)

    def __init__(self, text, start_date, end_date, leaser, taker, item):
        self.text=text
        self.start_date=start_date
        self.end_date=end_date
        self.leaser=leaser
        self.taker=taker
        self.item=item

class Feedback(Base):
    __tablename__ = 'feedback'
    
    id = Column(Integer, primary_key=True, autoincrement=True, unique=True, nullable=False)
    user = mapped_column(ForeignKey('user.id'), nullable=False)
    text = Column(String(255))
    grade = Column(Integer, nullable=False)
    contract = mapped_column(ForeignKey('contract.id'), unique=True, nullable=False)

    def __init__(self, user, text, grade , contract):
        self.user=user
        self.text=text
        self.grade=grade
        self.contract=contract