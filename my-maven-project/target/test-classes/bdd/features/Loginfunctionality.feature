Feature: Search and select health insurance on Policy Bazaar

  Scenario: User navigates to Policy Bazaar using a direct URL
    Given the user is on the Google homepage
    When the user navigates to "https://health.policybazaar.com/?pq=family0&new=1&utm_content=health_icon_hp_family_insurance_dom&utm_source=google_brand&utm_medium=ppc&utm_term=Policybazaar&utm_campaign=Brand_PolicyBazaar_Exact00PolicyBazaar&gad_source=1&gclid=EAIaIQobChMI6brew9b3igMVAqNmAh2OBi3pEAAYASAAEgJQtfD_BwE"
   #Then the user click on the continue button
    And the user closes the browser