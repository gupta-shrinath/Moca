from flask import jsonify
import requests,uuid
from app import db
class Meetup:
    @staticmethod    
    def get_meetups(group_name,topic_id):
        url = "https://api.meetup.com/" + group_name + "/events?page=20&fields=featured_photo" 
        response = requests.get(url)
        if not response.json():
            error = {
                "error" : "There is no upcoming meetup for Organization " + group_name
            }
            return jsonify(error)
        else:
            event_count = 0
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
                    'topic_id':topic_id
                }
                if(meetup['is_online_event'] == False):
                    event['venue'] = {
                        'name':meetup['venue']['name'],
                        'address':meetup['venue']['address_1']+" "+meetup['venue']['city']+" "+meetup['venue']['localized_country_name'],
                        'latitude':meetup['venue']['lat'],
                        'longitude':meetup['venue']['lon']
                    }
                if not db.events.find_one({'platform_link':meetup['link']}):
                    db.events.insert(event)
                    event_count = event_count + 1
            if event_count != 0:
                return jsonify({'status': event_count + ' events added'})
            else:
                return jsonify({'status':'0 events added'})

    @staticmethod
    def get_topic_meetups(group_name,topic_name,id):
        # Add featured_photo to field paramter to get meetup photo
        url = "https://api.meetup.com/" + group_name + "/events?page=20&fields=group_key_photo" 
        response = requests.get(url)
        if not response.json():
            error = {
                "error" : "There is no upcoming meetup for Organization" + group_name
            }
            return jsonify(error)
        else:
            event_count = 0
            for meetup in response.json():                    
                event = {
                    '_id':uuid.uuid4().hex,
                    'name':meetup['name'],
                    'description':meetup['description'],
                    #'image':meetup['featured_photo']['highres_link'],
                    'date':meetup['local_date'],
                    'time':meetup['local_time'],
                    'timezone':meetup['group']['timezone'],
                    #'duration':meetup['duration'],
                    'platform_link':meetup['link'],
                    'organization':meetup['group']['name'],
                    'organization_image':meetup['group']['key_photo']['thumb_link'],
                    #'organization_localized_location':meetup['group']['localized_location'],
                    #'rsvp_count':meetup['yes_rsvp_count'],
                    'is_online_event':meetup['is_online_event'],
                }
                name = meetup['name']
                if topic_name in name:
                    event['topic_id'] = id
                else:
                    event['topic_id'] = 100006
                if(meetup['is_online_event'] == False):
                    event['venue'] = {
                        'name':meetup['venue']['name'],
                        'address':meetup['venue']['address_1']+" "+meetup['venue']['city']+" "+meetup['venue']['localized_country_name'],
                        'latitude':meetup['venue']['lat'],
                        'longitude':meetup['venue']['lon']
                    }
                if not db.events.find_one({'platform_link':meetup['link']}):
                    db.events.insert(event)
                    event_count = event_count + 1
            if event_count != 0:
                return jsonify({'status': event_count + ' events added'})
            else:
                return jsonify({'status':'0 events added'})
