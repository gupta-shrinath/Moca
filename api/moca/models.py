from app import db
from flask import jsonify,request
import uuid

class Moca:
    @staticmethod
    def get_topics():
        return jsonify({
            'Kotlin':100001,
            'Javascript':100002,
            'Data Science':100003,
            'Web Development' : 100004,
            'Software Engineering' : 100006,
        }) 
        
    @staticmethod
    def get_all_events():
        event_list=[]
        result = db.events.find()
        for document in result:
            event_list.append(document)
        return jsonify(event_list)

    @staticmethod
    def get_events():
        if 'topic_id' in request.args:
            id = int(request.args['topic_id'])
            print(id)
            event_list=[]
            result = db.events.find({'topic_id':id})
            for document in result:
                event_list.append(document)
            return jsonify(event_list)  
        else:
            return jsonify({'error':'No topic_id field provided. Please specify an topic_id.'})


    @staticmethod
    def add_event():
        event = {
            '_id':uuid.uuid4().hex,
            'name':request.args['name'],
            'description':request.args['description'],
            'date':request.args['date'],
            'time':request.args['time'],
            'timezone':'Asia/Kolkata',
            'platform_link':request.args['platform_link'],
            'organization':request.args['organization'],
            'organization_image':request.args['organization_image'],
            'topic_id':request.args['topic_id']
        }
        if db.events.insert(event):
            return {'status':'sucess'}
        else:
            return {'status':'fail'}
