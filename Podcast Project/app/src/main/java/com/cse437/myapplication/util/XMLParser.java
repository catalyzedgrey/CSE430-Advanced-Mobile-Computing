package com.cse437.myapplication.util;

import android.util.Xml;

import com.cse437.myapplication.model.Episode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XMLParser {

    private static final String ns = null;

    public ArrayList parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private ArrayList readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("channel")) {
                entries = readItem(parser);
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    //    class Channel{
//        String item;
//        public Channel(String item){
//            this.item = item;
//        }
//    }
    private ArrayList<Episode> readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        ArrayList<Episode> epArrayList = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("item")) {
                epArrayList.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return epArrayList;
    }

    private Episode readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        String url = null;
        String length = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("description")) {
                description = readDescription(parser);
            } else if(name.equals("itunes:duration")){
                length = readDuration(parser);//readlength(parser);
            }
            else if (name.equals("enclosure")) {
                url = readUrl(parser);
                //length = readlength(parser);
                parser.nextTag();
            } else {
                skip(parser);
            }
        }
        return new Episode(title, description, url, length);
    }

    // Processes title tags in the feed.
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    // Processes description tags in the feed.
    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }
    // Processes description tags in the feed.
    private String readDuration(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "itunes:duration");
        String duration = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "itunes:duration");
        return duration;
    }

    // Processes description tags in the feed.
    private String readUrl(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "enclosure");
        //url="https://traffic.megaphone.fm/STA7192102916.mp3" length="226363350" type="audio/mpeg"

        return parser.getAttributeValue(null, "url");
    }
    private String readlength(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "enclosure");
        //url="https://traffic.megaphone.fm/STA7192102916.mp3" length="226363350" type="audio/mpeg"

        return parser.getAttributeValue(null, "length");
    }

    // Processes description tags in the feed.
//    private String readLength(XmlPullParser parser) throws IOException, XmlPullParserException {
//        parser.require(XmlPullParser.START_TAG, ns, "enclosure");
//        String description = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "description");
//        return description;
//    }

    // For the tags title and description, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }



//    private String readTaglessText(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String result = "";
//        String test = parser.getAttributeValue(null, "url");
//        //result = parser.getText();
//        parser.nextTag();
//
//        return result;
//    }


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
