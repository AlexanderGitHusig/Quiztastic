package quiztastic.app;

import quiztastic.core.Board;
import quiztastic.core.Question;
import quiztastic.domain.BoardController;
import quiztastic.domain.QuestionRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

public class Quiztastic {
    private static Quiztastic instance;

    public static Quiztastic getInstance() {
        if (instance == null) {
            InputStream s = Quiztastic.class
                    .getClassLoader()
                    .getResourceAsStream("master_season1-35clean.tsv");
            QuestionReader reader = new QuestionReader(new InputStreamReader(s));
            try {
                instance = new Quiztastic(MapQuestionRepository.fromQuestionReader(reader));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private final QuestionRepository questions;

    private Quiztastic(QuestionRepository questions) {
        this.questions = questions;
    }

    public Iterable<Question> getQuestions() {
        return questions.getQuestions();
    }

    public Board getBoard() {
        return new BoardController(questions).makeBoard();
    }
}
