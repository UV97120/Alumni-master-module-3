package vishal.alumni.xmlParser;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import vishal.alumni.model.UpcomingEvents;

public class XMLParser_Upcoming_Events {

    private static final String nameSpace = null;
    private ArrayList<UpcomingEvents> list = new ArrayList<>();
    private String title = null;

    public XMLParser_Upcoming_Events() {

    }

    public ArrayList<UpcomingEvents> parse(String response) {

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(response));
            parser.nextTag();
            readRoot(parser);
            return list;
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void readRoot(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, nameSpace, "upcoming");
        while (parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() == XmlPullParser.TEXT){
                continue;
            }
            String tag = parser.getName();
            if (tag.equals("events")) {
                readEventList(parser);
            } else {
                skip(parser);
            }
        }
    }

    private void readEventList(XmlPullParser parser) throws XmlPullParserException, IOException {

        UpcomingEvents upcomingEvents = new UpcomingEvents();
        parser.require(XmlPullParser.START_TAG, nameSpace, "events");
        while (parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() == XmlPullParser.TEXT){
                continue;
            }
            String tag = parser.getName();
            if(tag.equals("date")) {

                upcomingEvents.setDate(readDate(parser));
            }
            else if(tag.equals("heading"))
            {
                upcomingEvents.setHeading(readHeading(parser));
            }
            else if(tag.equals("category"))
            {
                //String category = readCategory(parser);
                upcomingEvents.setCategory(readCategory(parser));
                //Log.d("tagyu", "");
                list.add(upcomingEvents);
            }
            else {
                skip(parser);
            }

            Log.d("Enter","before list");

        }
    }


    private String readDate(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, nameSpace, "date");
        title = readText(parser);
        Log.d("LINKLABEL", title);
        parser.require(XmlPullParser.END_TAG, nameSpace, "date");
        return title;

    }


    private String readHeading(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, nameSpace, "heading");
        title = readText(parser);
        Log.d("LINKLABEL", title);
        parser.require(XmlPullParser.END_TAG, nameSpace, "heading");
        return title;

    }


    private String readCategory(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, nameSpace, "category");
        title = readText(parser);
        Log.d("LINKLABEL", title);
        parser.require(XmlPullParser.END_TAG, nameSpace, "category");
        return title;

    }


    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
