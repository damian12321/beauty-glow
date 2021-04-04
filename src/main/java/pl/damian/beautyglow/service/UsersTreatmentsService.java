package pl.damian.beautyglow.service;


import pl.damian.beautyglow.entity.UsersTreatments;

import java.util.Date;
import java.util.List;

public interface UsersTreatmentsService {

    public void addUsersTreatments(UsersTreatments usersTreatments);

    public void deleteUsersTreatments(int id);

    public void updateUsersTreatments(UsersTreatments usersTreatments);

    public UsersTreatments getUsersTreatments(int id);

    public List<UsersTreatments> getUsersTreatments();

    public List<UsersTreatments> getUsersTreatmentsOnSpecificDay(Date date);
}
