import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class QuestionManager {

    BufferedReader streamReader;
    StringBuilder jsonStringBuilder;

    InputStream questionDataAsInputStream;
    JSONObject questionDataAsJSON;

    Question[] questionArray;

    int questionCounter;

    public QuestionManager(){

        questionDataAsInputStream = getClass().getResourceAsStream("/questions.json");

        try{

            streamReader = new BufferedReader(new InputStreamReader(questionDataAsInputStream, "UTF-8"));
            jsonStringBuilder = new StringBuilder();

            String line;
            while ((line = streamReader.readLine()) != null){

                jsonStringBuilder.append(line);

            }

        } catch (IOException ioException){ioException.printStackTrace();}

        questionDataAsJSON = new JSONObject(jsonStringBuilder.toString());

        questionArray = new Question[70];

        // create Question Object from each question in JSON file

        for (int i= 0; i < questionArray.length; i++) {

            JSONObject questionAsJSON = (questionDataAsJSON.getJSONArray("questions").getJSONObject(i));

            String[] possibleAnswers = new String[3];

            possibleAnswers[0] = questionAsJSON.getJSONArray("possibleAnswers").getString(0);
            possibleAnswers[1] = questionAsJSON.getJSONArray("possibleAnswers").getString(1);
            possibleAnswers[2] = questionAsJSON.getJSONArray("possibleAnswers").getString(2);

            // shuffle the possible answers

            ArrayList<String> possibleAnswersAsList = new ArrayList<>(Arrays.asList(possibleAnswers));
            Collections.shuffle(possibleAnswersAsList);
            possibleAnswers = possibleAnswersAsList.toArray(possibleAnswers);

            questionArray[i] = new Question(
                    questionAsJSON.getString("id"),
                    questionAsJSON.getString("category"),
                    questionAsJSON.getString("difficulty"),
                    questionAsJSON.getString("question"),
                    possibleAnswers,
                    questionAsJSON.getString("correctAnswer")

            );

        }

        // shuffle questions

        ArrayList<Question> questionsAsList = new ArrayList<>(Arrays.asList(questionArray));
        Collections.shuffle(questionsAsList);
        questionArray = questionsAsList.toArray(questionArray);

        // initialize question counter

        questionCounter = 0;

    }

    public Question getQuestion(){

        questionCounter++;

        if (questionCounter > 71) {questionCounter = 1;}

        return questionArray[questionCounter - 1];

    }

}
