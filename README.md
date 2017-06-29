# javaee-unit-testing
javaee-unit-testing


#Index of examples
Testing Exception
  @Test(expected = )  - ClientTest - testNameIsNull() + testNameIsEmpty()
  try...catch         - ClientTest - testEmailIsNull()
  ExpectedException   - ClientTest - testRegistrationDateIsNull
  hasProperty         - ClientTest - testRegistrationDateIsBeforeToday
  ErrorCollector      - ClientTest - testClient()

The power of PowerMock
  getInternalState() - ClientTest - testAllowAccess()
  setInternalState() - ClientDAOImplTest - setup()
  whenNew - AuthenticationServerPowerMockTest - testLogin
  mockStatic - AuthenticationServerPowerMockTest - testLoginNoSuchAlgorithm


DataBuilder pattern
  ClientDataBuilder


Testing JPA
  ClientDAOImplTest

Annotations
  @BeforeClass - ClientDAOImplTest - setup()
  @Before      - beginTransaction()
  @After       - clearData()

Mock
  @Mock          - ClientServiceTest
  @InjectMocks   - ClientServiceTest
  when           - ClientServiceTest
  thenThrow      - ClientServiceTest
  Argument matchers - ClientServiceTest - testLoginWithWrongPassword   
  ArgumentCaptor - ClientServiceTest - testInsert() + testAllowAccess()


Missing
   spy
   doReturn()|doThrow()| doAnswer()|doNothing()|doCallRealMethod()
