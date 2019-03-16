package com.example.leehs.yottaproject06.FindLocation;

import java.util.List;

public interface OnFinishSearchListener {
	public void onSuccess(List<Item> itemList);
	public void onSuccessSingle(Item item);
	public void onFail();
}
