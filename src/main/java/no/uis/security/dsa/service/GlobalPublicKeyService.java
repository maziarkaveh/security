package no.uis.security.dsa.service;

import no.uis.security.common.service.BasicService;
import no.uis.security.dsa.model.GlobalPublicKey;
import no.uis.security.dsa.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: maziarkaveh
 * Date: 04.11.13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
public interface GlobalPublicKeyService  extends BasicService<GlobalPublicKey,Long> {
    GlobalPublicKey getLastGlobalPublicKey();
    GlobalPublicKey generateAndPersistNewGlobalPublicKey();

}
