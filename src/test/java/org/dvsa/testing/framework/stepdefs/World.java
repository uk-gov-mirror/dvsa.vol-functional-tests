package org.dvsa.testing.framework.stepdefs;

import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.CreateLicenceAPI;
import org.dvsa.testing.framework.Utils.API_CreateAndGrantAPP.GrantLicenceAPI;
import org.dvsa.testing.framework.Utils.Generic.GenericUtils;

public class World {
    public CreateLicenceAPI createLicence;
    public GrantLicenceAPI grantLicence;
    GenericUtils genericUtils;
}