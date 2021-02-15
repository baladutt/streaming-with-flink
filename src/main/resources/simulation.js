//print("hello")
logger.info("Running the simulation script")

var importIt = new JavaImporter(java.lang.String,java.util,java.io,com.stream.simulation,com.stream.fraud.model);  
with (importIt) {  
  var simulator = new Simulator();


  simulator.defineState("login")
  	.addResource("id", "account")
	.addAction("id", "login");
  simulator.defineState("logout")
  	.addResource("id", "account")
	.addAction("id", "logout");
  simulator.defineState("checkBalance")
  	.addResource("id", "account")
	.addAction("id", "readBalance");
  simulator.defineState("transferMoney")
  	.addResource("id", "account")
	.addResource("payee", "abc")
	.addAction("id", "transferMoney");
  simulator.defineState("movePhysically")
  	.addResource("id", null)
  	.addAction("id", null)
	.addSubject("physicalLocationX", "subject.getAttribute(\"physicalLocationX\")+(new java.util.Random()).nextInt(10000-0) + 0")
	.addSubject("physicalLocationY", "subject.getAttribute(\"physicalLocationX\")+(new java.util.Random()).nextInt(10000-0) + 0")
	.addSubject("time", "subject.getAttribute(\"time\") + (new java.util.Random()).nextInt(10000-0) + 0");
  simulator.defineState("moveVirtually")
  	.addResource("id", null)
  	.addAction("id", null)
	.addSubject("virtualLocation", "subject.getAttribute(\"virtualLocation\") + (new java.util.Random()).nextInt(10000-0) + 0")
	.addSubject("time", "subject.getAttribute(\"time\") + (new java.util.Random()).nextInt(10000-0) + 0");


  var stateTransitions = [
  	new StateTransition("login", "checkBalance", 1 , 1000),
	new StateTransition("checkBalance", "transferMoney", 1, 1000),
	new StateTransition("transferMoney", "logout", 1, 1000),
	new StateTransition("logout", "movePhysically", 1, 1000),
	new StateTransition("movePhysically", "login", 1, 1000)
  ];
  var stateTransitions1 = [
  	new StateTransition("login", "transferMoney", 1 , 1000),
	new StateTransition("transferMoney", "logout", 1, 1000)
  ];

  var resourceTemplate = new Resource();
  resourceTemplate.setAttribute("accountId","(new java.util.Random()).nextInt(10000-0) + 0");
  var resourcePool = simulator.definePool(100, resourceTemplate);
  var subjectTemplate = new Subject();
  subjectTemplate.setAttribute("id","(new java.util.Random()).nextInt(10000-0) + 0")
		 .setAttribute("physicalLocationX", "(new java.util.Random()).nextInt(10000-0) + 0")
		 .setAttribute("physicalLocationY", "(new java.util.Random()).nextInt(10000-0) + 0")
	         .setAttribute("virtualLocation","(new java.util.Random()).nextInt(10000-0) + 0")
	         .setAttribute("currentTime","(new java.util.Random()).nextInt(10000-0) + 0");
  var subjectPool = simulator.definePool(100, subjectTemplate);
  var subjectResourcePool = simulator.pair(resourcePool,subjectPool);
  var normalActorProperties = new HashMap();
  var fraudActorProperties = new HashMap();
  simulator.defineActor("normalActor", normalActorProperties).stateTransition("login", stateTransitions);
  simulator.defineActor("fraudActor", fraudActorProperties).stateTransition("login", stateTransitions1);

  simulator.startActors("normalActor", subjectResourcePool, 9);
  simulator.sleepInMilliSecs(10);
  simulator.startActors("fraudActor", subjectResourcePool, 0);
  simulator.sleepInMilliSecs(300000);
  simulator.end()
}  
result=simulator;
logger.info("Done - simulation script")

