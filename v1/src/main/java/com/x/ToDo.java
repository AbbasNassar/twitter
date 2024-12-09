package com.x;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
// N-Tier Architecture Implementation -> ToDo Class
public class ToDo {
    private String id;
    private String title;
    private String description;
    private boolean done;
    private ZonedDateTime createdOn;
    private ZonedDateTime updatedOn;

    public ToDo (){
        this.id = null;
        this.title = null;
        this.description = null; 
        this.done = false;
        this.createdOn = null;
        this.updatedOn = null;
    }
    public ToDo (String id, String title, String description, boolean done, ZonedDateTime createdOn, ZonedDateTime updatedOn){
        this.id = id;
        this.title = title;
        this.description = description; 
        this.done = done;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public ZonedDateTime getUpdatedOn() {
        return updatedOn;
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + (done ? 1231 : 1237);
        result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
        result = prime * result + ((updatedOn == null) ? 0 : updatedOn.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ToDo other = (ToDo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (done != other.done)
            return false;
        if (createdOn == null) {
            if (other.createdOn != null)
                return false;
        } else if (!createdOn.equals(other.createdOn))
            return false;
        if (updatedOn == null) {
            if (other.updatedOn != null)
                return false;
        } else if (!updatedOn.equals(other.updatedOn))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "ToDo [id=" + id + ", title=" + title + ", description=" + description + ", done=" + done
                + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]" + "\n";
    }

    
}
