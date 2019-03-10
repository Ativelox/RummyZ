package de.ativelox.rummyz.client.view.gui.manager;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.ativelox.rummyz.client.view.gui.property.IRenderable;
import de.ativelox.rummyz.client.view.gui.utils.AscendingComparator;

/**
 * A simple {@link IRenderManager} implementation, that sorts its renderables by
 * their layer ascendingly (higher layer gets drawn ontop of lower layers).
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 * 
 * @see IRenderable
 * @see IRenderManager
 *
 */
public final class RenderManager implements IRenderable, IRenderManager {

    /**
     * A list of all the renderables that are currently rendered by this manager.
     */
    private final List<IRenderable> mToRender;

    /**
     * The comparator used to sort the renderables.
     */
    private final Comparator<IRenderable> mComparator;

    /**
     * Creates a new {@link RenderManager}.
     * 
     */
    public RenderManager() {
	mToRender = new ArrayList<>();
	mComparator = new AscendingComparator<>(r -> r.getLayer());

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.manager.IRenderManager#add(de.ativelox.
     * rummyz.client.view.gui.property.IRenderable)
     */
    @Override
    public synchronized void add(final IRenderable renderable) {
	this.mToRender.add(renderable);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#getLayer()
     */
    @Override
    public int getLayer() {
	return 0;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.client.view.gui.manager.IRenderManager#remove(de.ativelox.
     * rummyz.client.view.gui.property.IRenderable)
     */
    @Override
    public synchronized void remove(final IRenderable renderable) {
	this.mToRender.remove(renderable);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.client.view.gui.property.IRenderable#render(java.awt.
     * Graphics)
     */
    @Override
    public synchronized void render(final Graphics g) {
	mToRender.sort(mComparator);

	for (IRenderable renderable : mToRender) {
	    renderable.render(g);

	}
    }
}
