from flask import Flask,jsonify,request
import requests
import uuid
from pymongo import MongoClient

app = Flask(__name__)

# MongoDB Config
mongoClient = MongoClient('localhost',33017)
db = mongoClient['moca']

from meetup import routes 
from moca import routes