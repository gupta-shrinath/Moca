# Moca API

## Technical Info.
- Flask
- MongoDB

## Hosting
- Heroku
- MongoDB Atlas

## Install
- Install python 3 
- run `pip install -r requirements.txt` 
- Set environment for MongoDB Connection String `MONGODB_CONNECTION_URL` and Eventbrite Oauth Personal Token `EVENTBRITE_OAUTH_PERSONAL_TOKEN`


## Endpoints
- To receive all topics 
https://moca-api.herokuapp.com/api/v1/resources/topics/ 
- To receive all events
https://moca-api.herokuapp.com/api/v1/resources/events/all/ 
- To receive topic specific events
https://moca-api.herokuapp.com/api/v1/resources/events?topic_id=topicId
    - Paramter 
        - topicId - The id of topic  
- To add event
https://moca-api.herokuapp.com/api/v1/resources/add_event
    - Parameters
        - name - The name of event
        - description - The description of event
        - date - The date of event of event
        - time - The time of event
        - platform_link -  The online meeting link  
        - organization - The event host
        - organization_image - The event host image
        - topic_id - The id of topic to which the event belong
