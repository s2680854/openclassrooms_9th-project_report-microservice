package com.mediscreen.service;

import com.mediscreen.model.Note;
import com.mediscreen.model.Report;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {

    public String generateReport(int age, String gender, Collection<Note> noteList) {

        Long triggersCount = getTriggersCount(noteList);
        String riskLevel = Report.NONE.toString();

        if (age > 30 && triggersCount >= 8L
        || gender == "M" && age < 30 && triggersCount >= 5L
        || gender == "F" && age < 30 && triggersCount >= 7L) {
            riskLevel = Report.EARLY_ONSET.toString();
        } else if (age > 30 && triggersCount >= 6L
                || gender == "M" && age < 30 && triggersCount >= 3L
                || gender == "F" && age < 30 && triggersCount >= 4L) {
            riskLevel = Report.IN_DANGER.toString();
        } else if (age > 30 && triggersCount >= 2L) {
            riskLevel = Report.BORDERLINE.toString();
        } else {
            riskLevel = Report.NONE.toString();
        }

        return riskLevel;
    }

    public Long getTriggersCount(Collection<Note> noteList) {

        HashMap<String, Boolean> triggers = new HashMap<>();
        triggers.put("Hémoglobine A1C", false);
        triggers.put("Microalbumine A1C", false);
        triggers.put("Taille A1C", false);
        triggers.put("Poids", false);
        triggers.put("Fumeur", false);
        triggers.put("Anormal", false);
        triggers.put("Cholestérol", false);
        triggers.put("Vertige", false);
        triggers.put("Rechute", false);
        triggers.put("Réaction", false);
        triggers.put("Anticorps", false);

        if (noteList.size() > 0) {
            noteList.stream().forEach(note -> {
                triggers.entrySet().forEach(entry -> {
                    if (note.getContent().contains(entry.getKey())) { entry.setValue(true); }
                });
            });
        }
        //triggers.entrySet().stream().forEach(entry -> System.out.println(entry.getValue()));
        Long triggersCount = triggers.entrySet()
                .stream().filter(entry -> entry.getValue().booleanValue())
                .count();

        return triggersCount;
    }

    public String getReport(String fullname, int age, String gender, Collection<Note> noteList) {

        Long triggersCount = getTriggersCount(noteList);
        String riskLevel = Report.NONE.toString();

        if (age > 30 && triggersCount >= 8L
                || gender == "M" && age < 30 && triggersCount >= 5L
                || gender == "F" && age < 30 && triggersCount >= 7L) {
            riskLevel = Report.EARLY_ONSET.toString();
        } else if (age > 30 && triggersCount >= 6L
                || gender == "M" && age < 30 && triggersCount >= 3L
                || gender == "F" && age < 30 && triggersCount >= 4L) {
            riskLevel = Report.IN_DANGER.toString();
        } else if (age > 30 && triggersCount >= 2L) {
            riskLevel = Report.BORDERLINE.toString();
        } else {
            riskLevel = Report.NONE.toString();
        }

        return "Patient: Test " + fullname + " (age " + age + ") diabetes assessment is: " + riskLevel;
    }
}
