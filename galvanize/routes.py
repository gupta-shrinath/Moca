from app import app
from galvanize.models import Galvanize
@app.route('/galvanize/events/')
def get_galvanizae_events():
    return Galvanize.get_events_details()
