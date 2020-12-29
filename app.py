from flask import Flask,jsonify,request
import requests
import uuid
from pymongo import MongoClient
import schedule
import time

app = Flask(__name__)

# MongoDB Config
mongoClient = MongoClient('localhost',33017)
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

schedule.every(10).seconds.do(get_events_from_sources())

while True:
    schedule.run_pending()
    time.sleep(1)