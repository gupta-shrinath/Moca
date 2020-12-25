from flask import Flask,jsonify,request
import requests
import uuid
from pymongo import MongoClient

app = Flask(__name__)

mongoClient = MongoClient('localhost',33017)
db = mongoClient['moca']

def get_meetups(group_name):
    url = "https://api.meetup.com/" + group_name + "/events?page=20&fields=featured_photo" 
    response = requests.get(url)
    if not response.json():
        error = {
            "error" : "There is no upcoming meetup for Organization" + group_name
        }
        return jsonify(error)
    else:
        event_list = []
        for meetup in response.json():
            event = {
                '_id':uuid.uuid4().hex,
                'name':meetup['name'],
                'description':meetup['description'],
                'image':meetup['featured_photo']['highres_link'],
                'date':meetup['local_date'],
                'time':meetup['local_time'],
                'duration':meetup['duration'],
                'platform_link':meetup['link'],
                'organization':meetup['group']['name'],
                'organization_localized_location':meetup['group']['localized_location'],
                'rsvp_count':meetup['yes_rsvp_count'],
                'is_online_event':meetup['is_online_event'],
                'topic_id':100001
            }
            if(meetup['is_online_event'] == False):
                event['venue'] = {
                    'name':meetup['venue']['name'],
                    'address':meetup['venue']['address_1']+" "+meetup['venue']['city']+" "+meetup['venue']['localized_country_name'],
                    'latitude':meetup['venue']['lat'],
                    'longitude':meetup['venue']['lon']
                }
            event_list.append(event)
        db.events.insert_many(event_list)
        return jsonify({'status':'success'})    


@app.route('/meetup/kotlin/')
#mavennow
def meetup_kotlin():
    return get_meetups('Kotlin-Mumbai')

@app.route('/api/v1/resources/events/all/')
def get_all_events():
    event_list=[]
    result = db.events.find()
    for document in result:
        event_list.append(document)
    return jsonify(event_list)

@app.route('/api/v1/resources/events/')
def get_events():
    if 'id' in request.args:
        id = int(request.args['id'])
        print(id)
        event_list=[]
        result = db.events.find({'topic_id':id})
        for document in result:
            event_list.append(document)
        return jsonify(event_list)  
    else:
        return jsonify({'error':'No id field provided. Please specify an id.'})

@app.route('/api/v1/resources/topics/')
def get_topics():
    return jsonify({
        'kotlin':100001,
    })