package com.epam.finalDemo.utils;

import com.epam.finalDemo.domain.TrainingType;
import com.epam.finalDemo.dto.request.*;
import org.springframework.stereotype.Component;

@Component
public class ValidModule {

    public void traineeRegister(TraineeRegistrationRequest request) {
        if (request.firstName() == null) {
            throw new IllegalArgumentException("First name is required");
        } else if (request.lastName() == null) {
            throw new IllegalArgumentException("Last name is required");
        }
    }

    public void trainerRegister(TrainerRegistrationRequest request) {
        if (request.firstName() == null) {
            throw new IllegalArgumentException("First name is required");
        } else if (request.lastName() == null) {
            throw new IllegalArgumentException("Last name is required");
        } else if (request.trainingType() == null) {
            throw new IllegalArgumentException("Training type is required");
        }
    }

    public void trainingTypeRegister(TrainingType request) {
        if (request.getName() == null) {
            throw new IllegalArgumentException("Training type name is required");
        }
    }

    public void changePassword(ChangeLoginRequest request) {
        if (request.username() == null) {
            throw new IllegalArgumentException("Username is required");
        } else if (request.oldPassword() == null) {
            throw new IllegalArgumentException("Old password is required");
        } else if (request.newPassword() == null) {
            throw new IllegalArgumentException("New password is required");
        }
    }

    public void trainingRequest(PostTrainingRequest request) {
        if (request.trainingName() == null) {
            throw new IllegalArgumentException("Training name is required");
        } else if (request.traineeUsername() == null) {
            throw new IllegalArgumentException("Trainee username is required");
        } else if (request.trainerUsername() == null) {
            throw new IllegalArgumentException("Trainer username is required");
        } else if (request.trainingDate() == null) {
            throw new IllegalArgumentException("Training date is required");
        } else if (request.duration() == null) {
            throw new IllegalArgumentException("Duration is required");
        }
    }

    public void changeStatus(ChangeStatusRequest request) {
        if (request.username() == null) {
            throw new IllegalArgumentException("Username is required");
        } else if (request.status() == null) {
            throw new IllegalArgumentException("Status is required");
        }
    }

    public void getProfile(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username is required");
        }
    }

    public void delete(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username is required");
        }
    }

    public void updateProfileTrainee(UpdateTraineeProfileRequest request) {
        if (request.username() == null) {
            throw new IllegalArgumentException("Username is required");
        } else if (request.firstName() == null) {
            throw new IllegalArgumentException("First name is required");
        } else if (request.lastName() == null) {
            throw new IllegalArgumentException("Last name is required");
        } else if (request.isActive() == null) {
            throw new IllegalArgumentException("Status is required");
        }
    }

    public void updateProfileTrainer(UpdateTrainerProfileRequest request) {
        if (request.username() == null) {
            throw new IllegalArgumentException("Username is required");
        } else if (request.firstName() == null) {
            throw new IllegalArgumentException("First name is required");
        } else if (request.lastName() == null) {
            throw new IllegalArgumentException("Last name is required");
        } else if (request.trainingType() == null) {
            throw new IllegalArgumentException("Training type is required");
        } else if (request.isActive() == null) {
            throw new IllegalArgumentException("Status is required");
        }
    }
}
