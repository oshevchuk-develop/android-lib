package com.gb.lib.adapters.rv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gb.lib.adapters.rv.holder.Holder;
import com.gb.lib.adapters.rv.i.IBaseAdapter;
import com.gb.lib.app.utils.Utils;
import com.gb.lib.view.holders.VHBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T extends BaseAdapter.Item, H extends VHBase> extends RecyclerView.Adapter<Holder<H>> implements IBaseAdapter<T, H> {

    @LayoutRes
    private int
            l;

    protected List<Item>
            items = new ArrayList<>();

    protected Context
            c;

    public BaseAdapter(
            Context c, int l) {
        this.c = c;
        this.l = l;
    }

    public BaseAdapter(
            Context c, int l, List<T> arg1) {
        this(c, l);
        this
                .oAdd(arg1);
    }

    public BaseAdapter(
            Context c, int l, Injection arg1, List<T> arg2) {
        this(c, l);

        this
                .oAdd(arg1)
                .oAdd(arg2);

    }

    /*clean and set*/
    public boolean data(
            List<T> arg1) {

        return this.oClear()
                .oAdd(arg1)
                .uiNotifAll().getItemCount() == 0;
    }

    public boolean data(
            List<T> arg1, Injection arg2) {

        return this.oClear()
                .oAdd(arg1)
                .oAdd(arg2)
                .uiNotifAll().getItemCount() == 0;
    }

    public boolean data(
            Injection arg1, List<T> arg2) {

        return this.oClear()
                .oAdd(arg1)
                .oAdd(arg2)
                .uiNotifAll().getItemCount() == 0;
    }

    public boolean data(
            Injection arg1, List<T> arg2, Injection arg3) {

        return this.oClear()
                .oAdd(arg1)
                .oAdd(arg2)
                .oAdd(arg3)
                .uiNotifAll().getItemCount() == 0;
    }

    public boolean data(
            Injection arg1, List<T> arg2, Injection arg3, List<T> arg4, Injection arg5, List<T> arg6) {

        return this.oClear()
                .oAdd(arg1)
                .oAdd(arg2)
                .oAdd(arg3)
                .oAdd(arg4)
                .oAdd(arg5)
                .oAdd(arg6)
                .uiNotifAll().getItemCount() == 0;
    }

    public boolean data(
            List<T> arg1, Injection arg2, List<T> arg3) {

        return this.oClear()
                .oAdd(arg1)
                .oAdd(arg2)
                .oAdd(arg3)
                .uiNotifAll().getItemCount() == 0;
    }

    /*add*/
    public boolean add(
            T arg1) {
        return this.add(arg1, this.items.size());
    }

    public boolean add(
            T arg1, int position) {
        if (this.items.size() > 0) {

            if (this.items.get((this.items.size() - 1)) instanceof InjectionPagination) {
                this.notifyItemChanged(
                        (this.items.size() - 1)
                );
            }
            if (this.items.get(0) instanceof InjectionPagination) {
                this.notifyItemChanged(
                        0
                );
            }
        }
        return this.oAdd(arg1, position).uiNotifInsert(position).getItemCount() == 0;
    }

    public boolean add(
            Injection arg1) {

        return
                this.add(arg1, this.items.size());
    }

    public boolean add(
            Injection arg1, int position) {

        return this.oAdd(arg1, position).uiNotifInsert(position).getItemCount() == 0;
    }

    public boolean add(
            List<T> arg1) {
        return this.add(arg1, this.items.size());
    }

    public boolean add(
            List<T> arg1, int position) {
        if (this.items.size() > 0) {

            if (this.items.get((this.items.size() - 1)) instanceof InjectionPagination) {
                this.notifyItemChanged(
                        (this.items.size() - 1)
                );
            }
            if (this.items.get(0) instanceof InjectionPagination) {
                this.notifyItemChanged(
                        0
                );
            }
        }
        return this.oAdd(arg1, position).uiNotifInsert(position, Utils.Lists.safe(arg1).size()).getItemCount() == 0;
    }


    public BaseAdapter<T, H> update(
            int i, T t) {
        this.items.set(i, t);
        return this;
    }

    public BaseAdapter<T, H> remove(
            int i) {
        this.items.remove(i);
        return this;
    }

    @NonNull
    @Override
    public Holder<H> onCreateViewHolder(@NonNull ViewGroup view, int i) {
        return
                new Holder<>((H) new VHBase(LayoutInflater.from(this.c).inflate(i, view, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder view, int position) {
        if (this.items.get(view.getAdapterPosition()) instanceof Injection) {
            this.bind(
                    (H) view.item, (Injection) this.items.get(view.getAdapterPosition()), view.getAdapterPosition());
        } else {
            this.bind(
                    (H) view.item, (T) this.items.get(view.getAdapterPosition()), view.getAdapterPosition());
        }
    }

    @Override
    public void bind(H holder, BaseAdapter.Injection item, int i) {

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return
                this.items.get(position).inst(Injection.class)
                        ? this.items.get(position).cast(Injection.class).getL()
                        : this.l;
    }

    public Item first() {
        if (this.items.size() > 0) {
            return
                    this.items.get(0);
        }
        return null;
    }

    public List<Item> selected() {
        List<Item>
                checked = new ArrayList<>();
        for (Item item : this.items) {
            if (item instanceof Selector && ((Selector) item).isSelected()) {
                checked.add(item);
            }
        }
        return checked;
    }

    public Item get(int i) {
        return this.items.get(i);
    }

    public BaseAdapter<T, H> select(int index, boolean multiple) {
        return select(index, multiple, true);
    }

    public BaseAdapter<T, H> select(int index, boolean multiple, boolean notif) {
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i) instanceof Selector) {
                if (multiple) {
                    if (i == index) {
                        ((Selector) this.items.get(i)).setSelected(!((Selector) this.items.get(i)).isSelected());
                    }
                } else {
                    ((Selector) this.items.get(i)).setSelected(i == index);
                }
            }
        }
        if (notif) {
            this.notifyDataSetChanged();
        }
        return this;
    }

    public static class Item implements Serializable {

        protected boolean inst(Class<?> cls) {
            return
                    cls != null && cls.isInstance(this);
        }

        protected <T extends Item> T cast(Class<T> cls) {
            return
                    this.inst(cls) ? cls.cast(this) : null;
        }
    }

    public static class Selector extends Item {

        protected boolean
                selected = false;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    public static class Injection extends Selector {

        private @LayoutRes
        int l;

        private int key = -1;

        public Injection(@LayoutRes int l) {
            this.l = l;
        }

        public Injection(@LayoutRes int l, int key) {
            this(l);
            this.key = key;
        }

        public int getL() {
            return l;
        }

        public int getK() {
            return key;
        }
    }

    public static class InjectionPagination extends Injection {

        public InjectionPagination(int l) {
            super(l);
        }
    }

    /*actions*/
    private BaseAdapter<T, H> oClear() {
        if (this.items != null) {
            this.items.clear();
        }
        return this;
    }

    private BaseAdapter<T, H> oAdd(T item) {
        return
                this.oAdd(item, this.items.size());
    }

    private BaseAdapter<T, H> oAdd(T item, int position) {
        if (item != null) {
            this.items.add(position, item);
        }
        return this;
    }

    private BaseAdapter<T, H> oAdd(Injection injection) {
        if (injection != null) {
            this.items.add(injection);
        }
        return this;
    }

    private BaseAdapter<T, H> oAdd(Injection injection, int position) {
        if (injection != null) {
            this.items.add(position, injection);
        }
        return this;
    }

    private BaseAdapter<T, H> oAdd(List<T> items) {
        return this.oAdd(items, this.items.size());
    }

    private BaseAdapter<T, H> oAdd(List<T> items, int position) {
        if (items != null) {
            this.items.addAll(position, items);
        }
        return this;
    }

    public BaseAdapter<T, H> uiNotifAll() {
        this
                .notifyDataSetChanged();
        return this;
    }

    public BaseAdapter<T, H> uiNotifInsert(int start, int count) {
        this
                .notifyItemRangeInserted(start, count);
        return this;
    }

    public BaseAdapter<T, H> uiNotifInsert(int position) {
        this
                .notifyItemInserted(position);
        return this;
    }
}
