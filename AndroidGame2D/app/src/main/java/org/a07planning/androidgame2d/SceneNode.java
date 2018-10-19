package org.a07planning.androidgame2d;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SceneNode
{
    private List<GameObject> objects = new ArrayList<>();
    private List<SceneNode> children = new ArrayList<>();

    public void draw(Canvas canvas)
    {
        for(GameObject object: objects)
        {
            object.draw(canvas);
        }
        for(SceneNode node: children)
        {
            node.draw(canvas);
        }
    }

    public void addGameSceneObject(GameObject object)
    {
        objects.add(object);
    }

    public void addChild(SceneNode child)
    {
        children.add(child);
    }

    public void removeObject(int id)
    {
        Iterator<GameObject> iterator= objects.iterator();
        while(iterator.hasNext())  {
            GameObject object = iterator.next();
            if(object.id == id) {
                // If explosion finish, Remove the current element from the iterator & list.
                iterator.remove();
                continue;
            }
        }
        for(SceneNode node: children)
        {
            node.removeObject(id);
        }
    }
}
