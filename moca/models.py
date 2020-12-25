from app import db
from flask import jsonify,request

class Moca:
    @staticmethod
    def get_topics():
        return jsonify({
            'kotlin':100001,
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