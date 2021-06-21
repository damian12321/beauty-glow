package pl.damian.beautyglow.service;


import pl.damian.beautyglow.entity.UsersTreatments;

import java.util.Date;
import java.util.List;

public interface UsersTreatmentsService {

     void addUsersTreatments(UsersTreatments usersTreatments);

     void deleteUsersTreatments(int id);

     void updateUsersTreatments(UsersTreatments usersTreatments);

     UsersTreatments getUsersTreatmentsById(int id);

     List<UsersTreatments> getUsersTreatments();

     List<UsersTreatments> getUsersTreatmentsOnSpecificDay(Date date);
}
