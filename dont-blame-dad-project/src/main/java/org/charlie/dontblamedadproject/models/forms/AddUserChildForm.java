package org.charlie.dontblamedadproject.models.forms;

import org.charlie.dontblamedadproject.models.Child;
import org.charlie.dontblamedadproject.models.User;

import javax.validation.constraints.NotNull;

public class AddUserChildForm {

    @NotNull
    private int userId;

    @NotNull
    private int childId;

    private Iterable<Child> children;

    private User user;

    public AddUserChildForm() {}

    public AddUserChildForm(Iterable<Child> children, User user) {
        this.children = children;
        this.user = user;

    }

    public int getUserId() {return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public int getChildId() {return childId; }

    public void setChildId(int childId) {this.childId= childId; }

    public Iterable<Child> getChildren() { return children; }

    public User getUser() {
        return user;
    }

}
