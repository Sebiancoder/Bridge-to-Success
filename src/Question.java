public class Question{

    public String id;
    public String category;
    public String difficulty;
    public String question;
    public String[] possibleAnswers;
    public String correctAnswer;

    public Question(String id, String category, String difficulty,
                    String question, String[] possibleAnswers, String correctAnswer){

        this.id = id;
        this.category = category;
        this.difficulty = difficulty;
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.correctAnswer = correctAnswer;

    }
}
