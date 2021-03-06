/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.example.widget;

import javax.sql.DataSource;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.main.Main;
import org.doslande.FtpToDivisionRoute;
import org.doslande.RestToDivisionRoute;
import org.doslande.ValidateGadgetRoute;
import org.doslande.ValidateWidgetRoute;

import com.mysql.cj.jdbc.MysqlDataSource;


/**
 * A plain Java Main to start the widget and gadget example.
 */
public final class WidgetMain {

    // use Camel Main to setup and run Camel
    private static Main main = new Main();

    private WidgetMain() {
        // to comply with checkstyle
    }

    public static void main(String[] args) throws Exception {
    	
    	
        DataSource dataSource = setupDataSource();
        
        // bind dataSource into the registry
        main.bind("myDataSource", dataSource);
    	
        // create the ActiveMQ component
        main.bind("activemq", createActiveMQComponent());
        
        PropertiesComponent pc = new PropertiesComponent();
        pc.setLocation("classpath:org/doslande/myprop.properties");
        main.bind("properties", pc);
        
        // from FTP to Widget Queue and Gadget Queue
//        main.addRouteBuilder(new FtpToDivisionRoute());
        
        // from REST to Divisions
        main.addRouteBuilder(new RestToDivisionRoute());
        
        main.addRouteBuilder(new ValidateWidgetRoute());
        
        main.addRouteBuilder(new ValidateGadgetRoute());

        // start and run Camel (block)
        main.run();
    }

    public static ActiveMQComponent createActiveMQComponent() {
        // you can set other options but this is the basic just needed

        ActiveMQComponent amq = new ActiveMQComponent();

        // The ActiveMQ Broker allows anonymous connection by default
        // amq.setUserName("admin");
        // amq.setPassword("admin");

        // the url to the remote ActiveMQ broker
        amq.setBrokerURL("tcp://localhost:61616");

        return amq;
    }
    
    private static DataSource setupDataSource() {
    	MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(3306);
        ds.setDatabaseName("david_schema");
        ds.setUser("david");
        ds.setPassword("david");
//        ds.setUrl(connectURI);
        return ds;
    }

}
