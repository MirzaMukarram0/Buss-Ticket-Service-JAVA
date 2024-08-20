package application;

import java.time.LocalDate;
import java.time.LocalTime;

public class Feedback {
    private int feedbackId;
    private LocalDate feedbackDate;
    private LocalTime feedbackTime;
    private String description;
    private int travellerId; // Foreign key for traveller

    public Feedback(LocalDate feedbackDate, LocalTime feedbackTime, String description, int travellerId) {
        this.setFeedbackDate(feedbackDate);
        this.setFeedbackTime(feedbackTime);
        this.setDescription(description);
        this.setTravellerId(travellerId);
    }

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public LocalDate getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(LocalDate feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public LocalTime getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(LocalTime feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTravellerId() {
		return travellerId;
	}

	public void setTravellerId(int travellerId) {
		this.travellerId = travellerId;
	}

}
