import os
from celery import Celery

broker_url = f'pyamqp://guest@{os.environ.get("RABBITMQ_HOST", "localhost")}//'

app = Celery('tasks', broker=broker_url)

@app.task
def add(x, y):
    print(x+y)