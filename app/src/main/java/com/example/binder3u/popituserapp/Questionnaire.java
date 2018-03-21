package com.example.binder3u.popituserapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cindy on 21/03/2018.
 */

public class Questionnaire {

    private List<Question> listeQuestions;

    public Questionnaire() {
        this.listeQuestions = new ArrayList<Question>();
        listeQuestions.add(new Question(1, "Je pense que je vais utiliser ce service fréquemment"));
        listeQuestions.add(new Question(2, "Je trouve ce service inutilement complexe"));
        listeQuestions.add(new Question(3, "Je pense que ce service est facile à utiliser"));
        listeQuestions.add(new Question(4, "Je pense que je vais devoir faire appel au support technique pour pouvoir utiliser ce service"));
        listeQuestions.add(new Question(5, "Je trouve qu’il y a beaucoup trop d’incohérences dans ce service"));
        listeQuestions.add(new Question(6, "Je trouve que les fonctionnalités du service sont bien intégrées"));
        listeQuestions.add(new Question(7, "Je trouve qu’il y a beaucoup trop d’incohérences dans ce service "));
        listeQuestions.add(new Question(8, "Je pense que la plupart des gens apprennent très rapidement à utiliser le service "));
        listeQuestions.add(new Question(9, "Je me suis senti très confiant en utilisant ce service"));
        listeQuestions.add(new Question(10, "J’ai dû apprendre beaucoup de choses avant de pouvoir utiliser ce service "));

    }

    public List<Question> getListeQuestions() {
        return listeQuestions;
    }

    public void setListeQuestions(List<Question> listeQuestions) {
        this.listeQuestions = listeQuestions;
    }
}
