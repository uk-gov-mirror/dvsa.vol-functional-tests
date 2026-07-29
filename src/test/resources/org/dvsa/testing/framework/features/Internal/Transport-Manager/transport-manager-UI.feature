@int_regression
@FullRegression
@transport_manager

Feature: Transport Manager - internal detail and processing tabs

  Background:
    Given i have a valid "goods" "standard_national" licence
    And i have logged in to internal as "admin"
    And I capture the transport manager id from the licence

  @Tm_details_tab
  Scenario: UI - Transport Manager Details tab loads
    When I navigate to the transport manager Details tab
    Then the transport manager "Details" page should be displayed at path "details/"

  @Tm_competences_tab
  Scenario: UI - Transport Manager Competences tab loads
    When I navigate to the transport manager Competences tab
    Then the transport manager "Competences" page should be displayed at path "details/competences/"

  @Tm_employment_tab
  Scenario: UI - Transport Manager Employment tab loads
    When I navigate to the transport manager Employment tab
    Then the transport manager "Other employment" page should be displayed at path "details/employment/"

  @Tm_previous_history_tab
  Scenario: UI - Transport Manager Previous History tab loads
    When I navigate to the transport manager Previous History tab
    Then the transport manager "Previous history" page should be displayed at path "details/previous-history/"

  @Tm_documents_tab
  Scenario: UI - Transport Manager Documents tab loads
    When I navigate to the transport manager Documents tab
    Then the transport manager "Documents" page should be displayed at path "documents/"

  @Tm_cases_tab
  Scenario: UI - Transport Manager Cases tab loads
    When I navigate to the transport manager Cases tab
    Then the transport manager "Cases" page should be displayed at path "cases/"

  @Tm_processing_notes_tab
  Scenario: UI - Transport Manager Processing Notes tab loads
    When I navigate to the transport manager Processing Notes tab
    Then the transport manager "Notes" page should be displayed at path "processing/notes/"

  @Tm_processing_event_history_tab
  Scenario: UI - Transport Manager Processing Change History tab loads
    When I navigate to the transport manager Processing Event History tab
    Then the transport manager "Change history" page should be displayed at path "processing/event-history/"

  @Tm_processing_publication_tab
  Scenario: UI - Transport Manager Processing Publication tab loads
    When I navigate to the transport manager Processing Publication tab
    Then the transport manager "Publication" page should be displayed at path "processing/publication/"

  @Tm_processing_read_history_tab
  Scenario: UI - Transport Manager Processing Access History tab loads
    When I navigate to the transport manager Processing Read History tab
    Then the transport manager "Access history" page should be displayed at path "processing/read-history/"

  # ------------------------------------------------------------------
  # Group 2 — Add-then-assert (create data via UI, verify row)
  # ------------------------------------------------------------------

  @Tm_add_processing_note
  Scenario: UI - Add a priority processing note to a Transport Manager
    When I add a processing note "Automated TM note - please ignore" to the transport manager with priority "Y"
    Then the transport manager Notes table should contain "Automated TM note - please ignore"

  @Tm_add_competence
  Scenario: UI - Add a competence to a Transport Manager
    When I add a competence "tm_qt_cpcsn" with serial "AUTO-CPC-12345" issued on "01" "02" "2020" to the transport manager
    Then the transport manager Competences table should contain serial "AUTO-CPC-12345"

  @Tm_add_employer
  Scenario: UI - Add an employer to a Transport Manager
    When I add an employer "Automated Employer Ltd" to the transport manager with position "Owner" hours "10"
    Then the transport manager Employment table should contain employer "Automated Employer Ltd"

  @Tm_upload_document
  Scenario: UI - Upload a document to a Transport Manager
    When I upload a document "AutomatedTmDocument" to the transport manager
    Then the transport manager Documents table should contain "AutomatedTmDocument"
