package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.models.ChannelModel;

public interface IChannelService {

	List<ChannelModel> getAllChannels();

	ChannelModel getChannelById(Long id);

	void addChannel(ChannelModel model);

	void updateChannel(Long id, ChannelModel model);

	void deleteChannel(Long id);
}
