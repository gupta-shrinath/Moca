from flask import Flask,jsonify,request
from apscheduler.schedulers.background import BackgroundScheduler
from apscheduler.executors.pool import ThreadPoolExecutor, ProcessPoolExecutor
import requests
import uuid
from pymongo import MongoClient
import time,os

app = Flask(__name__)

# MongoDB Config
mongodb_connection_string = os.environ.get('MONGODB_CONNECTION_URL')
mongoClient = MongoClient(mongodb_connection_string)
db = mongoClient['moca']

from meetup import routes
from galvanize import routes
from moca import routes
from meetup.models import Meetup
from galvanize.models import Galvanize

def get_events_from_sources():
    print('Scheduler Running')
    Meetup().get_meetups('kotlin-mumbai',100001)
    Meetup().get_topic_meetups('build-with-code-los-angeles','JavaScript',100002)
    Galvanize.get_events_details()   
    print('Scheduler Completed')

executors = {
    'default': ThreadPoolExecutor(16),
    'processpool': ProcessPoolExecutor(4)
}
sched = BackgroundScheduler(timezone='Asia/Kolkata', executors=executors)

sched.add_job(get_events_from_sources, 'cron', day_of_week=0)
sched.start()

