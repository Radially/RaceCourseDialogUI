package radial_design.racecoursedialogui;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jonathan on 22/07/2016.
 */
public class CourseXmlParser {
    private XmlPullParserFactory xmlFactory;
    private Context context;
    private String url;
    private List<String> names;
    private List<CourseType> courseTypes;
    private List<String[]> options;
    private XmlPullParser parser;

    public CourseXmlParser(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    public Mark parseXml(Map<String, String> selectedOptions) {
        Mark result = new Mark("null");
        try {
            InputStream stream = context.getApplicationContext().getAssets().open(url);
            xmlFactory = XmlPullParserFactory.newInstance();
            parser = xmlFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);

            result =  parseIt(parser, selectedOptions);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    public List<CourseType> parseCourseTypes(){
        try {
            InputStream stream = context.getApplicationContext().getAssets().open(url);
            xmlFactory = XmlPullParserFactory.newInstance();
            parser = xmlFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            courseTypes = getCourseTypes(parser);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courseTypes;
    }

    public List<String> parseCourseNames() {
        /*Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {*/
        try {
            InputStream stream = context.getApplicationContext().getAssets().open(url);
            xmlFactory = XmlPullParserFactory.newInstance();
            parser = xmlFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);

            names = getCourseNames(parser);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }/*
            }
        });
        thread.start();*/
        return names;
    }

    public List<String[]> parseCourseOptions(String courseType) {

        try {
            InputStream stream = context.getApplicationContext().getAssets().open(url);
            xmlFactory = XmlPullParserFactory.newInstance();
            parser = xmlFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);

            options = getCourseOptions(parser, courseType);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return options;
    }

    private Mark parseIt(XmlPullParser xmlPullParser, Map<String, String> selectedOptions) {
        int event;
        String text = null;
        Mark referenceMark = new Mark("Reference Point"); //reference point is represented as a mark, who is the father of all marks.
        ArrayList<Mark> fathers = new ArrayList<Mark>(); //preforms as a stack //to be able to add children to their father and know your location on the family tree. {grandfather(reference point), father, son, ...)
        Mark currentMark = new Mark("nullMark/debug");
        boolean preReceiveMode = false;
        boolean receiveMode = false;


        try {
            event = xmlPullParser.getEventType();
            String valueHolder = "";
            String attributeHolder = "";
            String name = "";
            while (event != XmlPullParser.END_DOCUMENT) {
                if (xmlPullParser.getName() != null) name = xmlPullParser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        fathers.add(referenceMark);  //the reference point is the father of all marks, so is the first to be used here.
                        break;
                    case XmlPullParser.START_TAG:
                        if (name.equals("Course") && safeAttributeValue("type").equals(selectedOptions.get("type"))) {
                            preReceiveMode = true;
                        } else if (name.equals("Legs") && preReceiveMode && safeAttributeValue("name").equals(selectedOptions.get("Legs"))) {
                            receiveMode=true;
                        } else if (name.equals("Mark")) {
                            currentMark = new Mark(safeAttributeValue("name")); //new mark
                            fathers.add(currentMark);  //son of his father
                        } else if(receiveMode&&name.equals("Distance")){
                            currentMark.setDistaneFactor(safeAttributeValue("factor"));
                        }
                        break;

                    case XmlPullParser.TEXT:
                        valueHolder = safeTextValue();
                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("Direction")) {
                            currentMark.setDirection(valueHolder);
                        } else if (name.equals("Distance")) {
                            currentMark.setDistance(valueHolder);
                            currentMark.setDistaneFactor(attributeHolder);
                        } else if (name.equals("GateType")) {
                            currentMark.setGateType(valueHolder);
                        }
                        else if (name.equals("GateDirection")) {
                            currentMark.setGateDirection(valueHolder);
                        }
                        else if (name.equals("GateDistance")) {
                            currentMark.setGateDistance(valueHolder);
                        }
                        else if (name.equals("Mark")) {
                            fathers.get(fathers.size() - 2).addReferedMark(currentMark);  //attach a son to his father
                            fathers.remove(fathers.size() - 1);  //son have no more children, so no longer necessary here
                        }
                        else if(name.equals("Legs") && receiveMode) {
                            preReceiveMode = false;
                            receiveMode = false;
                        }
                        break;
                }
                if(name.equals("Legs") && receiveMode) {
                    preReceiveMode = false;
                    receiveMode = false;
                    return referenceMark;
                }
                event = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return referenceMark;
    }

    private List<CourseType> getCourseTypes(XmlPullParser xmlPullParser) {
        int event;
        List<CourseType> courseTypes = new ArrayList<>();
        List<String> legs = new ArrayList<>(); // this is not a Spaghetti! maybe Penne or other italian names.
        options = new ArrayList<String[]>();
        try {
            event = xmlPullParser.getEventType();
            String attributeHolder;
            String name;
            String valueHolder="0";
            while (event != XmlPullParser.END_DOCUMENT) {
                name = xmlPullParser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        switch (name){
                            case "Course":
                                options= new ArrayList<String[]>();
                                legs = new ArrayList<>();
                                attributeHolder = safeAttributeValue("type");
                                courseTypes.add(new CourseType(attributeHolder));
                                break;
                            case "Legs":
                                legs.add(xmlPullParser.getAttributeValue(null, "name"));
                                break;
                            case "Mark":  //check 'isGatable' deeply
                                if (safeAttributeValue("isGatable").equals("true")){  //the is an optional gate
                                        attributeHolder = xmlPullParser.getAttributeValue(null, "gateType");  //attributeHolder restarts
                                        String[] gatable = {"", "toggle"};
                                        if (attributeHolder != null)
                                            gatable[0] = xmlPullParser.getAttributeValue(null, "name") + " " + attributeHolder;
                                        else
                                            gatable[0] = xmlPullParser.getAttributeValue(null, "name") + " Gate";
                                        options.add(gatable);
                                    }
                                break;

                        }
                        break;
                    case XmlPullParser.TEXT:
                        valueHolder = xmlPullParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        switch (name) {
                            case "Upwind":
                                courseTypes.get(courseTypes.size()-1).setCourseFactor(0,Double.parseDouble(valueHolder));
                                break;
                            case "Downwind":
                                courseTypes.get(courseTypes.size()-1).setCourseFactor(1,Double.parseDouble(valueHolder));
                                break;
                            case "Reach":
                                courseTypes.get(courseTypes.size()-1).setCourseFactor(2,Double.parseDouble(valueHolder));
                                break;
                            case "Course":
                                if (legs.size() > 1) {  //  --NOTICE--  bigger then 1
                                    legs.add(0, "Legs");
                                    legs.add(1, "spinner");
                                    String[] legsString = new String[legs.size()];
                                    legs.toArray(legsString);
                                    options.add(0, legsString);
                                }
                                courseTypes.get(courseTypes.size()-1).setOptions(options);
                                break;
                        }
                        break;

                }
                event = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courseTypes;
    }

    private List<String> getCourseNames(XmlPullParser xmlPullParser) {
        int event;
        List<String> coursesNames = new ArrayList<>();
        try {
            event = xmlPullParser.getEventType();
            String attributeHolder;
            String name;
            while (event != XmlPullParser.END_DOCUMENT) {
                name = xmlPullParser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (name.equals("Course")) {
                            /*attributeHolder = xmlPullParser.getAttributeValue(null,"type");
                            if(attributeHolder!=null){*/
                            attributeHolder = safeAttributeValue("type");
                            coursesNames.add(attributeHolder);
                            //}
                            //else Log.w("xml parser", "null Course 'type' attribute");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coursesNames;
    }

    private List<String[]> getCourseOptions(XmlPullParser xmlPullParser, String courseType) {
        int event;
        boolean reciveMode = false;
        List<String[]> options = new ArrayList<>();
        List<String> legs = new ArrayList<>(); // this is not a Spaghetti! maybe Penne or other italian names.


        try {
            event = xmlPullParser.getEventType();
            String attributeHolder;
            String valueHolder;
            String name;
            while (event != XmlPullParser.END_DOCUMENT) {
                name = xmlPullParser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (name.equals("Course")) {
                            attributeHolder = safeAttributeValue("type");
                            reciveMode = attributeHolder.equals(courseType);
                            Log.i("xml parser", "Course Options receive mode is now ON");

                        } else if (reciveMode && name.equals("Legs")) {
                            legs.add(xmlPullParser.getAttributeValue(null, "name"));
                        } else if (reciveMode && name.equals("Mark")) {
                            attributeHolder = xmlPullParser.getAttributeValue(null, "isGatable"); //attributeHolder restarts
                            if (attributeHolder != null && attributeHolder.equals("true")) {
                                attributeHolder = xmlPullParser.getAttributeValue(null, "gateType");  //attributeHolder restarts
                                String[] gatable = {"", "toggle"};
                                if (attributeHolder != null)
                                    gatable[0] = xmlPullParser.getAttributeValue(null, "name") + " " + attributeHolder;
                                else
                                    gatable[0] = xmlPullParser.getAttributeValue(null, "name") + " Gate";
                                options.add(gatable);
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        valueHolder = xmlPullParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("Course")) {
                            reciveMode = false;
                            Log.i("xml parser", "Course Options receive mode is now OFF");
                        }
                        break;
                }
                event = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (legs.size() > 1) {  //  --NOTICE--  bigger then 1
            legs.add(0, "Legs");
            legs.add(1, "spinner");
            String[] legsString = new String[legs.size()];
            legs.toArray(legsString);
            options.add(0, legsString);
        }
        return options;
    }

    private String safeAttributeValue(String keyName) {
        String value = parser.getAttributeValue(null, keyName);
        if (value != null) return value;
        Log.w("course xml parser", "null attribute for keyName: " + keyName);
        return "_";  // TODO: the '_' char is just for debug, remove before use.
    }
    private String safeTextValue() {
        String value = parser.getText();
        if (value != null) return value;
        Log.w("course xml parser", "null text found");
        return "_";  // TODO: the '_' char is just for debug, remove before use.
    }
}
