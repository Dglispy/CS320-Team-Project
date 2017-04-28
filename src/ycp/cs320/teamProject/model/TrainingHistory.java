package ycp.cs320.teamProject.model;

import java.util.ArrayList;
import java.util.PriorityQueue;
public class TrainingHistory {
	private User user;
	
	ArrayList<SOP> completedSOPs = new ArrayList<SOP>(); 
	//BinaryHeap<SOP> sopsToComplete = new BinaryHeap<SOP>();
	PriorityQueue<SOP> sopsToComplete = new PriorityQueue<SOP>();
	
	public TrainingHistory() {
		
	}
	
	public void addToCompleted(SOP sop) {
		completedSOPs.add(sop);
	}
	
	public void addToTodoList(SOP sop) {
		sopsToComplete.add(sop);
	}
	
	public int TrainingHistorySize() {
		return sopsToComplete.size() + completedSOPs.size();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	//possibly adding methods here to add and remove SOPs from their respective lists
}
