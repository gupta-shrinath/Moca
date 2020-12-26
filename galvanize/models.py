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
                        print(topic.getText())
                        event_topic.append(topic.getText())
        return eventbrite_events,event_topic     

    # Get id of all eventbrite url
    @staticmethod
    def get_eventbrite_event_ids(event_urls):
        event_ids = []
        for event in event_urls:
            substring = event.split("-")
            print(substring[-1])
            event_ids.append(substring[-1])
        return event_ids

    @staticmethod
    def get_date_in_indian_timezone(date):
        america_time_zone = pytz.timezone("America/Los_Angeles")
        india_time_zone = pytz.timezone("Asia/Kolkata")
        date = datetime.datetime.strptime(date,"%Y-%m-%d %H:%M:%S")
        date = america_time_zone.localize(date)
        date = date.astimezone(india_time_zone)
        date = date.strftime("%Y-%m-%d %H:%M:%S")
        new_date = date.split(" ")
        print(new_date[0])
        return new_date

    @staticmethod
    def get_events_details():
        urls,topics = Galvanize.get_galvanize_events()
        event_ids = Galvanize.get_eventbrite_event_ids(urls)
        topic_index = 0
        for event_id in event_ids:
            url = 'https://www.eventbriteapi.com/v3/events/'+event_id+'/'
            PERSONAL_OAUTH_TOKEN = "W43MZVDX2EYFUZENUUHX"
            header = {'Authorization': 'Bearer ' + PERSONAL_OAUTH_TOKEN}
            response = requests.get(url,headers=header)
            response = response.json()
            print(response['start'])
            response_start_date = response['start']['local']
            response_start_date = response_start_date.replace("T"," ")
            start_date = Galvanize.get_date_in_indian_timezone(response_start_date)[0]
            start_time = Galvanize.get_date_in_indian_timezone(response_start_date)[1]
            event = {
                    '_id':uuid.uuid4().hex,
                    'name': response['name']['text'],
                    'description':response['description']['text'],
                    'image':response['logo']['original']['url'],
                    'date':start_date,
                    'time':start_time,
                    'duration':"NA",
                    'platform_link':response['url'],
                    'organization':"Galavanize Remote",
                    'organization_localized_location':response['start']['timezone'],
                    'rsvp_count': "NULL",
                    'is_online_event':response['online_event'],
            }
            if topics[topic_index] == 'Data Science':
                event['topic_id'] = 100003
            if topics[topic_index] == 'Web Development':
                event['topic_id'] = 100004
            topic_index = topic_index + 1
            db.events.insert_one(event)
        return {'status':'sucess'}

