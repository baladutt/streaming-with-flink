//Define a fraud detection system
var systemName = "A1 Bank Fraud Detection System"
logger.info("setting up "+systemName)

//Load all other specification files
load("src/main/resources/A1BankFraudDetectionSystemAlerts.js");

var importIt = new JavaImporter(java.lang.String,java.util,java.io,java.time,com.stream);  
with (importIt) {  
  logger.info("setting up "+systemName)

  //The attributes that will be taken as features for ML. Rest will be ignored.
  fraudDetectionSystem.addFeatureAttribute("resource", "id", "categorical")
                      .addFeatureAttribute("action","id", "categorical")
                      .addFeatureAttribute("resource","amount", "double")
                      .addFeatureAttribute("environment","location-diff", "double")
                      .addFeatureAttribute("resource","amount-diff", "double");

  //The attributes based on which events will be selected. Rest of the events will be ignored.
  fraudDetectionSystem.addSelectorAttribute("resource", "tenant", "A1Bank");

  //The attributes that will be taken as features for ML. Rest will be ignored.
  fraudDetectionSystem.addML("HoeffdingTree")
                      .addML("HoeffdingAdaptiveTree")
                      .addML("NaiveBayes")
                      .addML("DecisionStump")
                      .addML("AdaHoeffdingOptionTree")
                      .addML("ExactSTORM")
                      .addML("ApproxSTORM")
                      .addML("MCOD")
                      .addML("SimpleCOD")
                      .addML("AbstractC");
        
  //Attributes for swimlane. Each swimlanes analyzes events in it, learns from it and maintains state.
  fraudDetectionSystem.addSwimlaneAttribute("resource", "id")
                      .addSwimlaneAttribute("resource", "accountId");
        
  //Attributes for keeping history. These keys decide the identity of the event. Same event may have two histories based on two keys.
  fraudDetectionSystem.addHistoryKeyAttribute("resource", "id", "5")
                      .addHistoryKeyAttribute("subject", "id", "5");
        
  //Attributes for keeping history. These keys decide the identity of the event. Same event may have two histories based on two keys.
  fraudDetectionSystem.addHistoryDiffAttribute("environment", "location", "diff", "environment", "location-diff")
                      .addHistoryDiffAttribute("environment", "time", "diff", "environment", "time-diff")
                      .addHistoryDiffAttribute("resource", "amount", "diff", "resource", "amount-diff");

}  
logger.info("Done setup of "+systemName)
result=fraudDetectionSystem;

