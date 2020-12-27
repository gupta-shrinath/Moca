from app import db
from flask import jsonify,request

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