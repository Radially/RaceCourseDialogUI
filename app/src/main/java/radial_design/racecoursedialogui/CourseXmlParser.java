package radial_design.racecoursedialogui;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 22/07/2016.
 */
public class CourseXmlParser {
    private XmlPullParserFactory xmlFactory;
    private Context context;
    private String url;
    private List<String> names;
    private List<String[]> options;

    public CourseXmlParser(Context context, String url) {
        this.context=context;
        this.url=url;
    }
    public void parseXml(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    InputStream stream = context.getApplicationContext().getAssets().open(url);
                    xmlFactory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = xmlFactory.newPullParser();

                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(stream, null);

                    parseIt(parser);
                    stream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    public List<String> parseCourseNames(){
        /*Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {*/
                try {
                    InputStream stream = context.getApplicationContext().getAssets().open(url);
                    xmlFactory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = xmlFactory.newPullParser();

                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(stream, null);

                    names= getCourseNames(parser);
                    stream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }/*
            }
        });
        thread.start();*/
        return names;
    }
    public List<String[]> parseCourseOptions(String courseType){
        /*Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {*/
        try {
            InputStream stream = context.getApplicationContext().getAssets().open(url);
            xmlFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);

            options= getCourseOptions(parser, courseType);
            stream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }/*
            }
        });
        thread.start();*/
        return options;
    }
    private List<String> getCourseNames(XmlPullParser xmlPullParser){
        int event;
        List<String> coursesNames = new ArrayList<>();
        try {
            event = xmlPullParser.getEventType();
            String attributeHolder;
            String name;
            while (event != XmlPullParser.END_DOCUMENT) {
                name=xmlPullParser.getName();
                switch (event){
                    case  XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if(name.equals("Course")){
                            attributeHolder = xmlPullParser.getAttributeValue(null,"type");
                            if(attributeHolder!=null){
                                coursesNames.add(attributeHolder);
                            }
                            else Log.w("xml parser", "null Course 'type' attribute");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = xmlPullParser.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return coursesNames;
    }
    private void parseIt(XmlPullParser xmlPullParser){
        int event;
        String text=null;
        Mark referenceMark= new Mark("Reference Point"); //reference point is represented as a mark, who is the father of all marks.
        ArrayList<Mark> fathers = new ArrayList<Mark>(); //preforms as a stack //to be able to add children to their father and know your location on the family tree. {grandfather(reference point), father, son, ...)
        Mark currentMark= new Mark("nullMark/debug");

        try {
            event = xmlPullParser.getEventType();
            //Toast.makeText(context,"Trying",Toast.LENGTH_LONG).show();  //cannot run - threads

            String valueHolder = "error! yayyy";
            String attributeHolder = "error! yayyy";
            String name = "error! yayyy";
            while (event != XmlPullParser.END_DOCUMENT) {
                if(xmlPullParser.getName()!=null)name=xmlPullParser.getName();
                switch (event){
                    case  XmlPullParser.START_DOCUMENT:
                        fathers.add(referenceMark);  //the reference point is the father of all marks, so is the first to be used here.
                        break;
                    case XmlPullParser.START_TAG:
                        if(name.equals("Legs")){
                            attributeHolder = xmlPullParser.getAttributeValue(null,"name");
                            if(attributeHolder!=null){
                                referenceMark= new Mark("Reference Point"); //new mark
                                fathers.add(currentMark);  //son of his father
                            }
                        }
                        else if(name.equals("Mark")){
                            attributeHolder = xmlPullParser.getAttributeValue(null,"name");
                            if(attributeHolder!=null){
                                currentMark = new Mark(attributeHolder); //new mark
                                fathers.add(currentMark);  //son of his father
                            }
                            else Log.w("Courses XML parser", "null value for name: "+name);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        valueHolder = xmlPullParser.getText();
                        if(valueHolder==null)Log.w("Courses XML parser", "null valueHolder");
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("Direction")){
                            currentMark.setDirection(valueHolder);
                        }
                        else if(name.equals("Distance")){
                            currentMark.setDistance(valueHolder);
                            currentMark.setDistaneFactor(attributeHolder);
                        }
                        if(xmlPullParser.getName().equals("Mark")){
                            fathers.get(fathers.size()-1).addReferedMark(currentMark);  //attach a son to his father
                            fathers.remove(fathers.size()-1);  //son have no more children, so no longer necessary here
                        }
                        break;
                }
                event = xmlPullParser.next();
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<String[]> getCourseOptions(XmlPullParser xmlPullParser, String courseType){
        int event;
        boolean reciveMode=false;
        List<String[]> options = new ArrayList<>();
        List<String> legs = new ArrayList<>(); // this is not a Spaghetti, maybe Penne or another italian name. :) :P


        try {
            event = xmlPullParser.getEventType();
            String attributeHolder;
            String valueHolder;
            String name;
            while (event != XmlPullParser.END_DOCUMENT) {
                name=xmlPullParser.getName();
                switch (event){
                    case  XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if(name.equals("Course")){
                            attributeHolder = xmlPullParser.getAttributeValue(null,"type");
                            reciveMode=attributeHolder.equals(courseType);
                            Log.i("xml parser","Course Options receive mode is now ON");

                        }
                        else if(reciveMode&&name.equals("Legs")){
                            legs.add(xmlPullParser.getAttributeValue(null,"name"));
                        }
                        else if(reciveMode&&name.equals("Mark")){
                            attributeHolder = xmlPullParser.getAttributeValue(null,"isGatable");
                            if(attributeHolder!=null&&attributeHolder.equals("true")){
                                String[] gatable = {xmlPullParser.getAttributeValue(null,"name")+" Gate","toggle"};
                                options.add(gatable);
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        valueHolder=xmlPullParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(name.equals("Course")){
                            reciveMode=false;
                            Log.i("xml parser","Course Options recive mode is now OFF");
                        }
                        break;
                }
                event = xmlPullParser.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(legs.size()>1){  //  --NOTICE--  bigger then 1
            legs.add(0,"Legs");
            legs.add(1,"spinner");
            String[] legsString  =new String[legs.size()];
            legs.toArray(legsString);
            options.add(0,legsString);
        }
        return options;
    }
}
