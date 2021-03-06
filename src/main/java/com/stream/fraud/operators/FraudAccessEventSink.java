package com.stream.fraud.operators;


import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import com.stream.fraud.model.FraudAccessEvent;
import com.stream.simulation.Publisher;


public class FraudAccessEventSink implements SinkFunction<FraudAccessEvent> {
	String streamName = null;

	private TraceSink<FraudAccessEvent> traceSink = new TraceSink<>();

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public FraudAccessEventSink(String streamName) {
		this.streamName = streamName;
    }

    @Override
    public void invoke(FraudAccessEvent fraudAccessEvent, Context context) throws Exception {
		traceSink.log(fraudAccessEvent, FraudAccessEventSink.class.getSimpleName(), "Stream Name="+this.streamName +", FRAUD!!! = "+fraudAccessEvent.toString());
		fraudAccessEvent.getEnvironment().setAttribute("detectionTime", new java.util.Date());
		Publisher.fireFraudAccessEvent(fraudAccessEvent);
    }
}
