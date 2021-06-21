package pl.damian.beautyglow.dao;

import pl.damian.beautyglow.entity.UsersTreatments;
import java.util.Date;
import java.util.List;

public interface UsersTreatmentsDao {
     void addUsersTreatments(UsersTreatments usersTreatments);

     void deleteUsersTreatments(int id);

     void updateUsersTreatments(UsersTreatments usersTreatments);

     UsersTreatments getUsersTreatmentsById(int id);

     List<UsersTreatments> getUsersTreatments();

     List<UsersTreatments> getUsersTreatmentsOnSpecificDay(Date date);

}
