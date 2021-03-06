package com.stream.simulation;

import java.util.Map.Entry;

import com.stream.fraud.model.AccessEvent;
import com.stream.fraud.model.Resource;
import com.stream.fraud.model.Subject;

public class Actor {
	
	/**
	 * @return the nameOfActor
	 */
	public String getNameOfActor() {
		return nameOfActor;
	}

	/**
	 * @param nameOfActor the nameOfActor to set
	 */
	public void setNameOfActor(String nameOfActor) {
		this.nameOfActor = nameOfActor;
	}

	private String nameOfActor;
	private String startStateName;
	private StateTransition [] stateTransitions;
	
	private Resource resource;
	private Subject subject;
	
	public Actor() {
		
	}
	
	public Actor(String nameOfActor) {
		this.nameOfActor = nameOfActor;
	}

	public void stateTransition(String startStateName, StateTransition [] stateTransitions) {
		this.setStartStateName(startStateName);
		this.setStateTransitions(stateTransitions);
		
	}

	public StateTransition [] getStateTransitions() {
		return stateTransitions;
	}

	public void setStateTransitions(StateTransition [] stateTransitions) {
		this.stateTransitions = stateTransitions;
	}

	public String getStartStateName() {
		return startStateName;
	}

	public void setStartStateName(String startStateName) {
		this.startStateName = startStateName;
	}

	public void setResource(Object object) {
		this.resource = (Resource)object;
	}
	
	public void setSubject(Object object) {
		this.subject = (Subject)object;
	}

	public AccessEvent ehanceAccessEvent(AccessEvent accessEvent) {
		if(resource != null ) {
			for(Entry<String, Object> entry: resource.getAttributes().entrySet()) {
				accessEvent.getResource().setAttribute(entry.getKey(), entry.getValue());
			}
		}
		if(subject != null ) {
			for(Entry<String, Object> entry: subject.getAttributes().entrySet()) {
				accessEvent.getSubject().setAttribute(entry.getKey(), entry.getValue());
			}
		}
		return accessEvent;
	}

}
