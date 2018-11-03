package cn.dankal.basiclib.protocol;

public interface HomeProtocol extends BaseRouteProtocol {
    String PART = "/home/";

    String HOMEACTIVITY=PART+"homeactivity";

    String HOMEDEMANDLIST=PART+"demandlist";

    String HOMESEARCH=PART+"homesearch";

    String HOMERELEASE=PART+"homerelease";

    String SUBMITIDEA=PART+"submitodea";

    String DEMANDDETA=PART+"demanddate";

    String CLAIMDEMAND=PART+"claimdemand";
}
