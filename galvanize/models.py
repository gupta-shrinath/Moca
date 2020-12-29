import requests
from bs4 import BeautifulSoup
from app import db
import json 
import pytz
import datetime
import uuid
class Galvanize:

    # Get all eventbrite events url and topic
    @staticmethod
    def get_galvanize_events():
        eventbrite_events = []
        event_topic = []
        url = 'https://www.galvanize.com/events'
        response = requests.get(url)
        soup = BeautifulSoup(response.content,'html.parser')
        results = soup.find_all('div',{'class':'action'})
        for result in results:
            if not result.contents:
                pass
            else:
                links = result.contents[0]['href']
                if "eventbrite" in links:
                    eventbrite_events.append(links)
                    if not result.previous_sibling:
                        pass
                    else :
                        topic_list = result.previous_sibling.find_all('p',{'class':'lead'})
                        for topic in topic_list:
                            event_topic.append(topic.getText())
        return eventbrite_events,event_topic     

    # Get id of all eventbrite url
    @staticmethod
    def get_eventbrite_event_ids(event_urls):
        event_ids = []
        for event in event_urls:
            substring = event.split("-")
            event_ids.append(substring[-1])
        return event_ids

    @staticmethod
    def get_events_details():
        urls,topics = Galvanize.get_galvanize_events()
        event_ids = Galvanize.get_eventbrite_event_ids(urls)
        topic_index = 0
        event_count = 0
        for event_id in event_ids:
            url = 'https://www.eventbriteapi.com/v3/events/'+event_id+'/?expand=organizer'
            PERSONAL_OAUTH_TOKEN = "W43MZVDX2EYFUZENUUHX"
            header = {'Authorization': 'Bearer ' + PERSONAL_OAUTH_TOKEN}
            response = requests.get(url,headers=header)
            response = response.json()
            start_date_time = response['start']['local']
            start_date_time = start_date_time.replace('T',' ')
            start_date_time = start_date_time.split()
            organizer_details_url = response['organizer']['resource_uri']
            organizer_details_response = requests.get(organizer_details_url,headers=header)
            organizer_details_response = organizer_details_response.json()
            event = {
                    '_id':uuid.uuid4().hex,
                    'name': response['name']['text'],
                    'description':response['description']['text'],
                    #'image':response['logo']['original']['url'],
                    'date':start_date_time[0],
                    'time':start_date_time[1],
                    'timezone':response['start']['timezone'],
                    #'duration':"NA",
                    'platform_link':response['url'],
                    'organization':"Galvanize Remote",
                    'is_online_event':response['online_event'],
                    #'organization_localized_location':response['start']['timezone'],
                    #'rsvp_count': "NULL",
            }
            if organizer_details_response['logo'] is None:
                event['organization_image'] = 'NA'
            else:
                event['organization_image'] = organizer_details_response['logo']['original']['url']


            if topics[topic_index] == 'Data Science':
                event['topic_id'] = 100003
            if topics[topic_index] == 'Web Development':
                event['topic_id'] = 100004
            topic_index = topic_index + 1
            if not db.events.find_one({'platform_link':response['url']}):
                db.events.insert_one(event)
                event_count = event_count + 1
        if event_count != 0:
                print('Galvanize Remote ', {'status': event_count + ' events added'})
                return {'status': event_count + ' events added'}
        else:
            print('Galvanize Remote',{'status':'0 events added'})
            return {'status':'0 events added'}

