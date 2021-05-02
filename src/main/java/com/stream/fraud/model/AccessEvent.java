package com.stream.fraud.model;

import java.util.UUID;

/**
 * Represents an access of Resource by a Subject
 * 
 * @author bdutt
 *
 */

public class AccessEvent extends Event{
	private String eventId = UUID.randomUUID().toString();
	
	//TODO: put time here
	
	private Subject subject = new Subject();
	private Resource resource = new Resource();
	private Action action = new Action();
	private Environment environment = new Environment();
	
	public AccessEvent() {
	}
	
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Environment getEnvironment() {
		return environment;
	}
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public String toString() {
		return "resource="+this.resource.getAttributes()+", action="+action.getAttributes()+", subject="+subject.getAttributes()+", environment="+environment.getAttributes()+", eventId="+getEventId();
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Object get(String[] attributeNameElements) {
		if(attributeNameElements.length >= 2) {
			AttributeContainer attributeContainer = null;
			switch(attributeNameElements[0]) {
			case "subject":
        		attributeContainer = getSubject();
        		break;
        	case "resource":
        		attributeContainer = getResource();
        		break;
        	case "action":
        		attributeContainer = getAction();
        		break;
        	case "environment":
        		attributeContainer = getEnvironment();
        		break;
			}
			if(null != attributeContainer) {
				Object val = attributeContainer.getAttribute(attributeNameElements[1]);
				return val;
			}
		}
		//We have no clue how to handle this
		//TODO: log error
		return null;
	}

}
