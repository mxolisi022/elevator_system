
.PHONY = build test run clean



help:
	@printf "\n\
	------------------------------------------------------------------\n\
	build:\t\tCompiles the project code.\n\
	test:\t\tRuns the test harness for the project.\n\
	run:\t\tLaunches the Lift Control System program.\n\
	clean:\t\tRemoves compiled code from the project directory.\n\
	------------------------------------------------------------------\n\
	\n"


build:
	@$(MAKE) clean
	@cd liftsystem; \
	mvn compile


test:
	@cd liftsystem; \
	mvn test


run:
	@cd liftsystem; \
	mvn exec:java


clean:
	@cd liftsystem; \
	mvn clean
