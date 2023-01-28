package com.huz.solr.transformer;

import com.huz.solr.model.User;
import com.huz.solr.model.UserTx;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

@Component
public class Transformer {
    public SolrInputDocument transform(UserTx dBUser){
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", dBUser.getId());
        document.addField("uuid", dBUser.getUuid());
        document.addField("first_name", dBUser.getFirstName());
        document.addField("last_name", dBUser.getLastName());
        document.addField("company", dBUser.getCompany());
        document.addField("address", dBUser.getAddress());
        document.addField("city", dBUser.getCity());
        document.addField("county", dBUser.getCounty());
        document.addField("state_province", dBUser.getStateProvince());
        document.addField("zip_postal_code", dBUser.getZipPostalCode());
        document.addField("email", dBUser.getEmail());
        document.addField("web", dBUser.getWeb());
        return document;
    }
}
