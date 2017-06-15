package fr.lip6.move.gal.itstools.launch.devTools;

import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.GestureListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SegmentListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ITS_Text{

	private Text text;
	
	public ITS_Text(Composite composite, int multi) {
		text = new Text(composite, multi);
		text.setLayoutData(new GridData());
	}

	public int hashCode() {
		return text.hashCode();
	}

	public Rectangle computeTrim(int x, int y, int width, int height) {
		return text.computeTrim(x, y, width, height);
	}

	public boolean equals(Object obj) {
		return text.equals(obj);
	}

	public Rectangle getClientArea() {
		return text.getClientArea();
	}

	public ScrollBar getHorizontalBar() {
		return text.getHorizontalBar();
	}

	public int getScrollbarsMode() {
		return text.getScrollbarsMode();
	}

	public ScrollBar getVerticalBar() {
		return text.getVerticalBar();
	}

	public void addListener(int eventType, Listener listener) {
		text.addListener(eventType, listener);
	}

	public int getTextDirection() {
		return text.getTextDirection();
	}

	public void addModifyListener(ModifyListener listener) {
		text.addModifyListener(listener);
	}

	public void addDisposeListener(DisposeListener listener) {
		text.addDisposeListener(listener);
	}

	public void addSegmentListener(SegmentListener listener) {
		text.addSegmentListener(listener);
	}

	public void addSelectionListener(SelectionListener listener) {
		text.addSelectionListener(listener);
	}

	public void addVerifyListener(VerifyListener listener) {
		text.addVerifyListener(listener);
	}

	public void append(String string) {
		text.append(string);
	}

	public void dispose() {
		text.dispose();
	}

	public boolean print(GC gc) {
		return text.print(gc);
	}

	public Object getData() {
		return text.getData();
	}

	public Object getData(String key) {
		return text.getData(key);
	}

	public Display getDisplay() {
		return text.getDisplay();
	}

	public Listener[] getListeners(int eventType) {
		return text.getListeners(eventType);
	}

	public void clearSelection() {
		text.clearSelection();
	}

	public int getStyle() {
		return text.getStyle();
	}

	public Point computeSize(int wHint, int hHint) {
		return text.computeSize(wHint, hHint);
	}

	public Point computeSize(int wHint, int hHint, boolean changed) {
		return text.computeSize(wHint, hHint, changed);
	}

	public void copy() {
		text.copy();
	}

	public void cut() {
		text.cut();
	}

	public Accessible getAccessible() {
		return text.getAccessible();
	}

	public Rectangle getBounds() {
		return text.getBounds();
	}

	public int getCaretLineNumber() {
		return text.getCaretLineNumber();
	}

	public boolean isAutoDirection() {
		return text.isAutoDirection();
	}

	public boolean isDisposed() {
		return text.isDisposed();
	}

	public void setBounds(Rectangle rect) {
		text.setBounds(rect);
	}

	public Point getCaretLocation() {
		return text.getCaretLocation();
	}

	public boolean isListening(int eventType) {
		return text.isListening(eventType);
	}

	public void setBounds(int x, int y, int width, int height) {
		text.setBounds(x, y, width, height);
	}

	public int getCaretPosition() {
		return text.getCaretPosition();
	}

	public void notifyListeners(int eventType, Event event) {
		text.notifyListeners(eventType, event);
	}

	public int getCharCount() {
		return text.getCharCount();
	}

	public boolean getDoubleClickEnabled() {
		return text.getDoubleClickEnabled();
	}

	public char getEchoChar() {
		return text.getEchoChar();
	}

	public void removeListener(int eventType, Listener listener) {
		text.removeListener(eventType, listener);
	}

	public boolean getEditable() {
		return text.getEditable();
	}

	public int getLineCount() {
		return text.getLineCount();
	}

	public String getLineDelimiter() {
		return text.getLineDelimiter();
	}

	public int getLineHeight() {
		return text.getLineHeight();
	}

	public String getMessage() {
		return text.getMessage();
	}

	public void reskin(int flags) {
		text.reskin(flags);
	}

	public int getOrientation() {
		return text.getOrientation();
	}

	public Point getLocation() {
		return text.getLocation();
	}

	public void removeDisposeListener(DisposeListener listener) {
		text.removeDisposeListener(listener);
	}

	public Point getSelection() {
		return text.getSelection();
	}

	public void setLocation(Point location) {
		text.setLocation(location);
	}

	public void setLocation(int x, int y) {
		text.setLocation(x, y);
	}

	public int getSelectionCount() {
		return text.getSelectionCount();
	}

	public Point getSize() {
		return text.getSize();
	}

	public String getSelectionText() {
		return text.getSelectionText();
	}

	public int getTabs() {
		return text.getTabs();
	}

	public void setSize(Point size) {
		text.setSize(size);
	}

	public String getText() {
		return text.getText();
	}

	public String getText(int start, int end) {
		return text.getText(start, end);
	}

	public void setRegion(Region region) {
		text.setRegion(region);
	}

	public void setData(Object data) {
		text.setData(data);
	}

	public char[] getTextChars() {
		return text.getTextChars();
	}

	public void setData(String key, Object value) {
		text.setData(key, value);
	}

	public void setSize(int width, int height) {
		text.setSize(width, height);
	}

	public int getTextLimit() {
		return text.getTextLimit();
	}

	public boolean isAutoScalable() {
		return text.isAutoScalable();
	}

	public int getTopIndex() {
		return text.getTopIndex();
	}

	public void moveAbove(Control control) {
		text.moveAbove(control);
	}

	public void moveBelow(Control control) {
		text.moveBelow(control);
	}

	public int getTopPixel() {
		return text.getTopPixel();
	}

	public void pack() {
		text.pack();
	}

	public void pack(boolean changed) {
		text.pack(changed);
	}

	public void setLayoutData(Object layoutData) {
		text.setLayoutData(layoutData);
	}

	public Point toControl(int x, int y) {
		return text.toControl(x, y);
	}

	public Point toControl(Point point) {
		return text.toControl(point);
	}

	public Point toDisplay(int x, int y) {
		return text.toDisplay(x, y);
	}

	public Point toDisplay(Point point) {
		return text.toDisplay(point);
	}

	public void addControlListener(ControlListener listener) {
		text.addControlListener(listener);
	}

	public void addDragDetectListener(DragDetectListener listener) {
		text.addDragDetectListener(listener);
	}

	public void addFocusListener(FocusListener listener) {
		text.addFocusListener(listener);
	}

	public String toString() {
		return text.toString();
	}

	public void addGestureListener(GestureListener listener) {
		text.addGestureListener(listener);
	}

	public void addHelpListener(HelpListener listener) {
		text.addHelpListener(listener);
	}

	public void addKeyListener(KeyListener listener) {
		text.addKeyListener(listener);
	}

	public void addMenuDetectListener(MenuDetectListener listener) {
		text.addMenuDetectListener(listener);
	}

	public void addMouseListener(MouseListener listener) {
		text.addMouseListener(listener);
	}

	public void addMouseMoveListener(MouseMoveListener listener) {
		text.addMouseMoveListener(listener);
	}

	public void addMouseTrackListener(MouseTrackListener listener) {
		text.addMouseTrackListener(listener);
	}

	public void addMouseWheelListener(MouseWheelListener listener) {
		text.addMouseWheelListener(listener);
	}

	public void addPaintListener(PaintListener listener) {
		text.addPaintListener(listener);
	}

	public void addTouchListener(TouchListener listener) {
		text.addTouchListener(listener);
	}

	public void addTraverseListener(TraverseListener listener) {
		text.addTraverseListener(listener);
	}

	public void removeControlListener(ControlListener listener) {
		text.removeControlListener(listener);
	}

	public void removeDragDetectListener(DragDetectListener listener) {
		text.removeDragDetectListener(listener);
	}

	public void insert(String string) {
		text.insert(string);
	}

	public void removeFocusListener(FocusListener listener) {
		text.removeFocusListener(listener);
	}

	public void removeGestureListener(GestureListener listener) {
		text.removeGestureListener(listener);
	}

	public void paste() {
		text.paste();
	}

	public void removeHelpListener(HelpListener listener) {
		text.removeHelpListener(listener);
	}

	public void removeKeyListener(KeyListener listener) {
		text.removeKeyListener(listener);
	}

	public void removeModifyListener(ModifyListener listener) {
		text.removeModifyListener(listener);
	}

	public void removeMenuDetectListener(MenuDetectListener listener) {
		text.removeMenuDetectListener(listener);
	}

	public void removeSegmentListener(SegmentListener listener) {
		text.removeSegmentListener(listener);
	}

	public void removeMouseListener(MouseListener listener) {
		text.removeMouseListener(listener);
	}

	public void removeSelectionListener(SelectionListener listener) {
		text.removeSelectionListener(listener);
	}

	public void removeMouseMoveListener(MouseMoveListener listener) {
		text.removeMouseMoveListener(listener);
	}

	public void removeVerifyListener(VerifyListener listener) {
		text.removeVerifyListener(listener);
	}

	public void removeMouseTrackListener(MouseTrackListener listener) {
		text.removeMouseTrackListener(listener);
	}

	public void selectAll() {
		text.selectAll();
	}

	public void removeMouseWheelListener(MouseWheelListener listener) {
		text.removeMouseWheelListener(listener);
	}

	public void removePaintListener(PaintListener listener) {
		text.removePaintListener(listener);
	}

	public void removeTouchListener(TouchListener listener) {
		text.removeTouchListener(listener);
	}

	public void setDoubleClickEnabled(boolean doubleClick) {
		text.setDoubleClickEnabled(doubleClick);
	}

	public void removeTraverseListener(TraverseListener listener) {
		text.removeTraverseListener(listener);
	}

	public void setEchoChar(char echo) {
		text.setEchoChar(echo);
	}

	public boolean dragDetect(Event event) {
		return text.dragDetect(event);
	}

	public void setEditable(boolean editable) {
		text.setEditable(editable);
	}

	public boolean dragDetect(MouseEvent event) {
		return text.dragDetect(event);
	}

	public void setMessage(String message) {
		text.setMessage(message);
	}

	public void setOrientation(int orientation) {
		text.setOrientation(orientation);
	}

	public void setSelection(int start) {
		text.setSelection(start);
	}

	public void setSelection(int start, int end) {
		text.setSelection(start, end);
	}

	public void setSelection(Point selection) {
		text.setSelection(selection);
	}

	public boolean forceFocus() {
		return text.forceFocus();
	}

	public void setTabs(int tabs) {
		text.setTabs(tabs);
	}

	public Color getBackground() {
		return text.getBackground();
	}

	public void setText(String string) {
		text.setText(string);
	}

	public Image getBackgroundImage() {
		return text.getBackgroundImage();
	}

	

	public int getBorderWidth() {
		return text.getBorderWidth();
	}

	public Cursor getCursor() {
		return text.getCursor();
	}

	public boolean getDragDetect() {
		return text.getDragDetect();
	}

	public boolean getEnabled() {
		return text.getEnabled();
	}

	public Font getFont() {
		return text.getFont();
	}

	public void setTextLimit(int limit) {
		text.setTextLimit(limit);
	}

	public Color getForeground() {
		return text.getForeground();
	}

	public void setTopIndex(int index) {
		text.setTopIndex(index);
	}

	public Object getLayoutData() {
		return text.getLayoutData();
	}

	public void showSelection() {
		text.showSelection();
	}

	public Menu getMenu() {
		return text.getMenu();
	}

	public Monitor getMonitor() {
		return text.getMonitor();
	}

	public Composite getParent() {
		return text.getParent();
	}

	public Region getRegion() {
		return text.getRegion();
	}

	public Shell getShell() {
		return text.getShell();
	}

	public String getToolTipText() {
		return text.getToolTipText();
	}

	public boolean getTouchEnabled() {
		return text.getTouchEnabled();
	}

	public boolean getVisible() {
		return text.getVisible();
	}

	public long internal_new_GC(GCData data) {
		return text.internal_new_GC(data);
	}

	public void internal_dispose_GC(long hDC, GCData data) {
		text.internal_dispose_GC(hDC, data);
	}

	public boolean isReparentable() {
		return text.isReparentable();
	}

	public boolean isEnabled() {
		return text.isEnabled();
	}

	public boolean isFocusControl() {
		return text.isFocusControl();
	}

	public boolean isVisible() {
		return text.isVisible();
	}

	public void requestLayout() {
		text.requestLayout();
	}

	public void redraw() {
		text.redraw();
	}

	public void redraw(int x, int y, int width, int height, boolean all) {
		text.redraw(x, y, width, height, all);
	}

	public void setBackground(Color color) {
		text.setBackground(color);
	}

	public void setBackgroundImage(Image image) {
		text.setBackgroundImage(image);
	}

	public void setCapture(boolean capture) {
		text.setCapture(capture);
	}

	public void setCursor(Cursor cursor) {
		text.setCursor(cursor);
	}

	public void setDragDetect(boolean dragDetect) {
		text.setDragDetect(dragDetect);
	}

	public void setEnabled(boolean enabled) {
		text.setEnabled(enabled);
	}

	public boolean setFocus() {
		return text.setFocus();
	}

	public void setFont(Font font) {
		text.setFont(font);
	}

	public void setForeground(Color color) {
		text.setForeground(color);
	}

	public void setMenu(Menu menu) {
		text.setMenu(menu);
	}

	public boolean setParent(Composite parent) {
		return text.setParent(parent);
	}

	public void setRedraw(boolean redraw) {
		text.setRedraw(redraw);
	}

	public void setTextDirection(int textDirection) {
		text.setTextDirection(textDirection);
	}

	public void setToolTipText(String string) {
		text.setToolTipText(string);
	}

	public void setTouchEnabled(boolean enabled) {
		text.setTouchEnabled(enabled);
	}

	public void setVisible(boolean visible) {
		text.setVisible(visible);
	}

	public boolean traverse(int traversal) {
		return text.traverse(traversal);
	}

	public boolean traverse(int traversal, Event event) {
		return text.traverse(traversal, event);
	}

	public boolean traverse(int traversal, KeyEvent event) {
		return text.traverse(traversal, event);
	}

	public void update() {
		text.update();
	}

	

}
