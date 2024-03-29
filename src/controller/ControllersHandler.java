package controller;

public class ControllersHandler {
    private static final ControllersHandler instance = new ControllersHandler();
    public static ControllersHandler getInstance() {
        return instance;
    }

    private MainViewController mainViewController = null;
    private SimulationViewController simulationViewController = null;
    private AdminViewController adminViewController = null;
    private SalesmanViewController salesmanViewController = null;
    private StatisticsViewController statisticsViewController = null;

    public MainViewController getMainViewController() {
        return mainViewController;
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public SimulationViewController getSimulationViewController() {
        return simulationViewController;
    }

    public void setSimulationViewController(SimulationViewController simulationViewController) {
        this.simulationViewController = simulationViewController;
    }

    public AdminViewController getAdminViewController() {
        return adminViewController;
    }

    public void setAdminViewController(AdminViewController adminViewController) {
        this.adminViewController = adminViewController;
    }

    public SalesmanViewController getSalesmanViewController() {
        return salesmanViewController;
    }

    public void setSalesmanViewController(SalesmanViewController salesmanViewController) {
        this.salesmanViewController = salesmanViewController;
    }

    public StatisticsViewController getStatisticsViewController() {
        return statisticsViewController;
    }

    public void setStatisticsViewController(StatisticsViewController statisticsViewController) {
        this.statisticsViewController = statisticsViewController;
    }
}
