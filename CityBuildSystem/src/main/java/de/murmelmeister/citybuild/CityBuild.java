package de.murmelmeister.citybuild;

public final class CityBuild extends Main {

    private static CityBuild instance;

    private InitPlugin initPlugin;

    @Override
    public void onDisable() {
        getInitPlugin().onDisable();
        handleDisableMessage();
    }

    @Override
    public void onEnable() {
        setInitPlugin(new InitPlugin());
        getInitPlugin().onEnable();
        handleEnableMessage();
    }

    @Override
    public void onLoad() {
        setInstance(this);
    }

    public static CityBuild getInstance() {
        return instance;
    }

    public static void setInstance(CityBuild instance) {
        CityBuild.instance = instance;
    }

    public InitPlugin getInitPlugin() {
        return initPlugin;
    }

    public void setInitPlugin(InitPlugin initPlugin) {
        this.initPlugin = initPlugin;
    }
}
